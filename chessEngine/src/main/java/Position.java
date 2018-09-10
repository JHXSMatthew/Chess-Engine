public class Position {
    public int[] board = new int[128];

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











