package connect4;

import java.util.Scanner;

import connect4.response.ListStrategiesResponse;
import connect4.response.MoveResponse;
import connect4.response.NewGameResponse;
import connect4.response.OKResponse;
import connect4.response.StatusResponse;

public class CommandLineDriver 
{

public static void main(String[] args)
{
    String baseUrl = "http://euclid.knox.edu:8083/connect4";
    String user = "aisha"; 
    Connector conn = new Connector(baseUrl, user);
    
    System.out.println("Welcome!");
    help();
    try (Scanner scanner = new Scanner(System.in)) {
        while (true) {
            String command = scanner.next();
            if (command.equals("new")) {
                NewGameResponse response = conn.newGame();
                System.out.println(response.toString());
            } else if (command.equals("move")) {
                // client-side validation before sending
                if (!scanner.hasNextInt()) {
                    System.out.println("Error: column must be a number.");
                    scanner.next(); // discard bad input
                    continue;
                }
                int col = scanner.nextInt();
                if (col < 0 || col > 6) {
                    System.out.println("Error: column must be between 0 and 6.");
                    continue;
                }
                try {
                    MoveResponse move = conn.makeMove(col);
                    System.out.println(move);
                    if (move.gameOver) {
                        System.out.println("Game over! Winner: " + move.winner);
                        System.out.println("Type 'new' to play again or 'quit' to exit.");
                    }
                } catch (connect4.response.Connect4Error e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (command.equals("quit")) {
                break;
            } else if (command.equals("status")) {
                try {
                    StatusResponse status = conn.status();
                    System.out.println(status.toString());
                } catch (connect4.response.Connect4Error e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (command.equals("list")) {
                ListStrategiesResponse list = conn.listStrategies();
                for (Strategy strategy : list.getStrategies()) {
                    System.out.println(strategy.name + ": " + strategy.description);
                }
            } else if (command.equals("strategy")) {
                String strategy = scanner.next();
                try {
                    conn.setStrategy(strategy);
                    System.out.println("Strategy set to " + strategy);
                } catch (connect4.response.Connect4Error e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else if (command.equals("help")) {
                help();
            } else {
                System.out.println("Unknown command: " + command + ". Type 'help' for options.");
            }
        }
    }
    System.out.println("Goodbye");
}

    private static void help() {
        System.out.println("new - start a new game");
        System.out.println("move <col> - make a move in column <col>");
        System.out.println("quit - quit the game");
        System.out.println("status - check the status of the game");
        System.out.println("list - list the available strategies");
        System.out.println("strategy <name> - choose the given server strategy");
        System.out.println("help - show this help message");
    }
}
