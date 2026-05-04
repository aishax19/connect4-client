package connect4.response;

public class Util {
    public static String boardToString(int[][] board) {
    StringBuilder sb = new StringBuilder();
    sb.append("  0 1 2 3 4 5 6\n");
    sb.append("  -------------\n");
    for (int r = 0; r < board.length; r++) {
        sb.append("| ");
        for (int c = 0; c < board[r].length; c++) {
            char cell = board[r][c] == 1 ? 'X' : board[r][c] == 2 ? 'O' : '.';
            sb.append(cell).append(" ");
        }
        sb.append("|\n");
    }
    sb.append("  -------------\n");
    return sb.toString();
}

}
