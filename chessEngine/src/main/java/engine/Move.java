package engine;
import java.util.*;

public class Move {
    private int type; //normal, special (en passant, castle, promotion)
    private int originSquare;
    private int targetSquare;
    private int originPiece;
    private int targetPiece;
    private int promotion;  //promotion pieceType

    static final int NORMAL = 0;
    static final int PROMOTION = 1;
    static final int CASTLE = 2;
    static final int ENPASSANT_ENABLER = 3;
    static final int ENPASSANT_CAPTURE = 4;
    static final int EMPTY = 5;

    public Move () {
        this.type = EMPTY;
        this.originSquare = Square.NOSQUARE;
        this.targetSquare = Square.NOSQUARE;
        this.originPiece = Piece.NO_PIECE;
        this.targetPiece = Piece.NO_PIECE;
        this.promotion = Piece.NO_PIECE_TYPE;
    }

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
        this.originSquare = Board.toSquare(originSquare);
        this.targetSquare = Board.toSquare(targetSquare);
        this.originPiece = Piece.NO_PIECE;
        this.targetPiece = Piece.NO_PIECE;
        this.promotion = Piece.NO_PIECE_TYPE;
    }

    public static List<Move> generateWhiteKingSideMoves() {
        ArrayList<Move>  moves = new ArrayList<Move>();
        Move m = new Move(NORMAL, Square.WHITE_KING_STARTING_SQUARE, Square.WHITE_KING_STARTING_SQUARE + Square.E,
                Piece.WHITE_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);
        m = new Move(NORMAL, Square.WHITE_KING_STARTING_SQUARE + Square.E, Square.WHITE_KINGSIDE_FINISHING_SQUARE, Piece.WHITE_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);

        return moves;
    }
    public static List<Move> generateWhiteQueenSideMoves() {
        ArrayList<Move>  moves = new ArrayList<Move>();
        Move m = new Move(NORMAL, Square.WHITE_KING_STARTING_SQUARE, Square.WHITE_KING_STARTING_SQUARE + Square.W,
                Piece.WHITE_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);
        m = new Move(NORMAL, Square.WHITE_KING_STARTING_SQUARE + Square.W, Square.WHITE_QUEENSIDE_FINISHING_SQUARE, Piece.WHITE_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);

        return moves;
    }
    public static List<Move> generateBlackKingSideMoves() {
        ArrayList<Move>  moves = new ArrayList<Move>();
        Move m = new Move(NORMAL, Square.BLACK_KING_STARTING_SQUARE, Square.BLACK_KING_STARTING_SQUARE + Square.E,
                Piece.BLACK_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);
        m = new Move(NORMAL, Square.BLACK_KING_STARTING_SQUARE + Square.E, Square.BLACK_KINGSIDE_FINISHING_SQUARE, Piece.BLACK_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);

        return moves;
    }
    public static List<Move> generateBlackQueenSideMoves() {
        ArrayList<Move>  moves = new ArrayList<Move>();
        Move m = new Move(NORMAL, Square.BLACK_KING_STARTING_SQUARE, Square.BLACK_KING_STARTING_SQUARE + Square.W,
                Piece.BLACK_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);
        m = new Move(NORMAL, Square.BLACK_KING_STARTING_SQUARE + Square.W, Square.BLACK_QUEENSIDE_FINISHING_SQUARE, Piece.BLACK_KING, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
        moves.add(m);

        return moves;
    }


    public void setOriginPiece (Board b) {
        if (Square.isValid(originSquare)) {
            originPiece = b.board[originSquare];
        }
    }

    public void setTargetPiece (Board b) {
        if (Square.isValid(targetSquare)) {
            targetPiece = b.board[targetSquare];
        }
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
