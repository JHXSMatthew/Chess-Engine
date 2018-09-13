public class Move {
    private int type; //normal, special (en passant, castle, promotion)
    private int originSquare;
    private int targetSquare;
    private int originPiece;
    private int targetPiece;
    private int promotion;  //promotion pieceType

    public final int NORMAL = 0;


    public Move(int type, int originSquare, int targetSquare, int originPiece, int targetPiece, int promotion) {
        this.type = type;
        this.originSquare = originSquare;
        this.targetSquare = targetSquare;
        this.originPiece = originPiece;
        this.targetPiece = targetPiece;
        this.promotion = promotion;
    }

    public Move(int originSquare, int targetSquare) {
        this.type = NORMAL;
        this.originSquare = Position.toSquare(originSquare);
        this.targetSquare = Position.toSquare(targetSquare);
        this.originPiece = Piece.NO_PIECE;
        this.targetPiece = Piece.NO_PIECE;
        this.promotion = Piece.NO_PIECE_TYPE;
    }

    public int getType() {
        return type;
    }

    public int getOriginSquare() {
        return originSquare;
    }

    public int getTargetSquare() {
        return targetSquare;
    }

    public int getOriginPiece() {
        return originPiece;
    }

    public int getTargetPiece() {
        return targetPiece;
    }

    public int getPromotion() {
        return promotion;
    }
}
