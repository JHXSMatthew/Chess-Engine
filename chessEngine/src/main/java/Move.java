public class Move {
    private int type;
    private int originSquare;
    private int targetSquare;
    private int originPiece;
    private int targetPiece;
    private int promotion;

    public Move(int type, int originSquare, int targetSquare, int originPiece, int targetPiece, int promotion) {
        this.type = type;
        this.originSquare = originSquare;
        this.targetSquare = targetSquare;
        this.originPiece = originPiece;
        this.targetPiece = targetPiece;
        this.promotion = promotion;
    }
}
