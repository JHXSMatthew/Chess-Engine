public class Position {
    public int[] board = new int[128];
    public long[][] pieces = new long[2][6]; // first index is colour, second index is piece type
    public int activeColour;

    public Position () {
        for (int value: Square.values) {
            board[value] = Piece.NO_PIECE;
        }
    }

    public int[] makeMove (Move m) {
        int[] boardCopy = new int[128];
        System.arraycopy(board, 0, boardCopy, 0, board.length);
        return boardCopy;
    }

    public int toSquare (int rank, int file) {
        return 16 * rank + file;
    }

    public int rank(int square) {
        return square >> 4;
    }

    public int toSquare (int index) {
        return index + (index & ~7);
    }

    public int toIndex (int square) {
        return (square + (square & 7)) >> 1;
    }
}











