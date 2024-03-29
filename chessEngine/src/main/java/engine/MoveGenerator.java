package engine;
import java.util.*;

public class MoveGenerator {
    private List<Move> moves;

    public static int userMode = 0;
    public static int aiMode = 1;

    public int[] targetSquareToIndexArray() {
        int[] targetSquares = new int[moves.size()];

        int index = 0;
        for (Move m: moves) {
            targetSquares[index] = Board.toIndex(m.getTargetSquare());
            index++;
        }
        return targetSquares;
    }

    public MoveGenerator() {
        this.moves = new ArrayList<Move>();
    }

    public void emptyMoves() {
        moves.clear();
    }

    public List<Move> getMoves() {
        return moves;
    }

    //Generates all available moves for the currently activeColour
    public void generateMoves(Board p, int mode) {
        for (int originSquare: Square.values) {
            int originPiece = p.board[originSquare];
            if (!Piece.isValid(originPiece)) {
                continue;
            }
            if (Piece.getColour(originPiece) != p.activeColour) {
                continue;
            }
            int originType = Piece.getType(originPiece);
            int[] directions = Square.getDirection(p.activeColour, originType);

            if (originType == Piece.PAWN) {
                int max = 1;
                if ((p.activeColour == Piece.BLACK && p.rank(originSquare) == 1) || (p.activeColour == Piece.WHITE && p.rank(originSquare) == 6)) {
                    max = 2;
                }
                for (int multiplier = 1; multiplier <= max; multiplier++) {
                    int currentSquare = originSquare + directions[0] * multiplier;
                    if (Square.isValid(currentSquare) && p.board[currentSquare] == Piece.NO_PIECE) {
                        //pawn promotion
                        if (p.activeColour == Piece.BLACK && p.rank(currentSquare) == 7 || p.activeColour == Piece.WHITE && p.rank(currentSquare) == 0) {
                            if (mode == userMode) {
                                Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            } else if (mode == aiMode) {
                                Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.QUEEN);
                                moves.add(m);
                                m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.KNIGHT);
                                moves.add(m);
                                m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.ROOK);
                                moves.add(m);
                                m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.BISHOP);
                                moves.add(m);
                            } else {
                                throw new IllegalArgumentException();
                            }

                        } else if (multiplier == 2) {
                            Move m = new Move(Move.ENPASSANT_ENABLER, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        } else {
                            Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        }
                    } else {
                        break;
                    }
                }
                for (int remainingDirections = 1; remainingDirections < 3; remainingDirections++) {
                    int currentSquare = originSquare + directions[remainingDirections];
                    if (Square.isValid(currentSquare)) {
                        if (currentSquare == p.enPassantSquare && p.board[p.enPassantSquare] == Piece.NO_PIECE) {
                            Move m = new Move(Move.ENPASSANT_CAPTURE, originSquare, currentSquare, originPiece, Piece.valueOf(Piece.oppositeColour(p.activeColour), Piece.PAWN), Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        } else if (p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                            if (p.activeColour == Piece.BLACK && p.rank(currentSquare) == 7 || p.activeColour == Piece.WHITE && p.rank(currentSquare) == 0) {
                                if (mode == userMode) {
                                    Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                                    moves.add(m);
                                } else if (mode == aiMode) {
                                    Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.QUEEN);
                                    moves.add(m);
                                    m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.KNIGHT);
                                    moves.add(m);
                                    m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.ROOK);
                                    moves.add(m);
                                    m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.BISHOP);
                                    moves.add(m);
                                } else {
                                    throw new IllegalArgumentException();
                                }

                            } else {
                                Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            }

                        }
                    }
                }
            } else if (!Piece.isSliding(originType)) { //kings/knights
                for (int i = 0; i < directions.length; i++) {
                    int currentSquare = originSquare + directions[i];
                    if (Square.isValid(currentSquare) && p.board[currentSquare] == Piece.NO_PIECE) {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    } else if (Square.isValid(currentSquare) && p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    }
                }
            } else { //sliding pieces
                for (int i = 0; i < directions.length; i++) {
                    int currentSquare = originSquare + directions[i];
                    while (Square.isValid(currentSquare) ) {
                        if (p.board[currentSquare] == Piece.NO_PIECE) {
                            Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        } else if (p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                            Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                            moves.add(m);
                            break;
                        } else if (p.board[currentSquare] != Piece.NO_PIECE) {
                            break;
                        }
                        currentSquare = currentSquare + directions[i];
                    }
                }
            }

            //castling
            if (originType == Piece.KING && !p.isChecked(p.activeColour)) {
                if (p.getCastleQueenSide(p.activeColour)) {
                    boolean success = true;
                    if (p.activeColour == Piece.BLACK) { //check if any pieces between king and queenside rook
                        for (int square = Square.BLACK_QUEENSIDE_ROOK_STARTING_SQUARE + 1; square < Square.BLACK_KING_STARTING_SQUARE; square++) {
                            if (p.board[square] != Piece.NO_PIECE) {
                                success = false;
                                break;
                            }
                        }
                        if (success) {
                            Board copy = p.copy(p);
                            for (Move m: Move.generateBlackQueenSideMoves()) {
                                p.applyMove(m);
                                if (p.isChecked(p.activeColour)) {
                                    success = false;
                                    break;
                                }
                            }
                            p.restoreBoard(copy);
                            if (success) {
                                Move m = new Move(Move.CASTLE, originSquare, Square.BLACK_KING_STARTING_SQUARE + Square.W + Square.W,
                                        originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            }
                        }
                    } else { //check if there are any pieces between the king and queenside rook
                        for (int square = Square.WHITE_QUEENSIDE_ROOK_STARTING_SQUARE + 1; square < Square.WHITE_KING_STARTING_SQUARE; square++) {
                            if (p.board[square] != Piece.NO_PIECE) {
                                success = false;
                                break;
                            }
                        }
                        if (success) {
                            Board copy = p.copy(p);
                            for (Move m: Move.generateWhiteQueenSideMoves()) {
                                p.applyMove(m);
                                if (p.isChecked(p.activeColour)) {
                                    success = false;
                                    break;
                                }
                            }
                            p.restoreBoard(copy);
                            if (success) {
                                Move m = new Move(Move.CASTLE, originSquare, Square.WHITE_KING_STARTING_SQUARE + Square.W + Square.W,
                                        originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            }
                        }
                    }
                }
                if (p.getCastleKingSide(p.activeColour)) {
                    boolean success = true;
                    if (p.activeColour == Piece.BLACK) {
                        for (int square = Square.BLACK_KING_STARTING_SQUARE + 1; square < Square.BLACK_KINGSIDE_ROOK_STARTING_SQUARE; square++) {
                            if (p.board[square] != Piece.NO_PIECE) {
                                success = false;
                                break;
                            }
                        }
                        if (success) {
                            Board copy = p.copy(p);
                            for (Move m: Move.generateBlackKingSideMoves()) {
                                p.applyMove(m);
                                if (p.isChecked(p.activeColour)) {
                                    success = false;
                                    break;
                                }
                            }
                            p.restoreBoard(copy);
                            if (success) {
                                Move m = new Move(Move.CASTLE, originSquare, Square.BLACK_KING_STARTING_SQUARE + Square.E + Square.E,
                                        originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            }
                        }
                    } else {
                        for (int square = Square.WHITE_KING_STARTING_SQUARE + 1; square < Square.WHITE_KINGSIDE_ROOK_STARTING_SQUARE; square++) {
                            if (p.board[square] != Piece.NO_PIECE) {
                                success = false;
                                break;
                            }
                        }
                        if (success) {
                            Board copy = p.copy(p);
                            for (Move m: Move.generateWhiteKingSideMoves()) {
                                p.applyMove(m);
                                if (p.isChecked(p.activeColour)) {
                                    success = false;
                                    break;
                                }
                            }
                            p.restoreBoard(copy);
                            if (success) {
                                Move m = new Move(Move.CASTLE, originSquare, Square.WHITE_KING_STARTING_SQUARE + Square.E + Square.E,
                                        originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            }
                        }
                    }
                }
            }
        }
    }


    public void generateMoves(int originSquare, Board p, int mode) {
        if (!Square.isValid(originSquare)) {
            return;
        }

        int originPiece = p.board[originSquare];
        if (!Piece.isValid(originPiece)) {
            return;
        }
        if (Piece.getColour(originPiece) != p.activeColour) {
            return;
        }
        int originType = Piece.getType(originPiece);
        int[] directions = Square.getDirection(p.activeColour, originType);
        if (originType == Piece.PAWN) {
            int max = 1;
            if ((p.activeColour == Piece.BLACK && p.rank(originSquare) == 1) || (p.activeColour == Piece.WHITE && p.rank(originSquare) == 6)) {
                max = 2;
            }
            for (int multiplier = 1; multiplier <= max; multiplier++) {
                int currentSquare = originSquare + directions[0] * multiplier;
                if (Square.isValid(currentSquare) && p.board[currentSquare] == Piece.NO_PIECE) {
                    //pawn promotion
                    if (p.activeColour == Piece.BLACK && p.rank(currentSquare) == 7 || p.activeColour == Piece.WHITE && p.rank(currentSquare) == 0) {
                        if (mode == userMode) {
                            Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        } else if (mode == aiMode) {
                            Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.QUEEN);
                            moves.add(m);
                            m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.KNIGHT);
                            moves.add(m);
                            m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.ROOK);
                            moves.add(m);
                            m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.BISHOP);
                            moves.add(m);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } else if (multiplier == 2) {
                        Move m = new Move(Move.ENPASSANT_ENABLER, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    } else {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    }
                } else {
                    break;
                }
            }
            for (int remainingDirections = 1; remainingDirections < 3; remainingDirections++) {
                int currentSquare = originSquare + directions[remainingDirections];
                if (Square.isValid(currentSquare)) {
                    if (currentSquare == p.enPassantSquare && p.board[p.enPassantSquare] == Piece.NO_PIECE) {
                        Move m = new Move(Move.ENPASSANT_CAPTURE, originSquare, currentSquare, originPiece, Piece.valueOf(Piece.oppositeColour(p.activeColour), Piece.PAWN), Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    } else if (p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                        if (p.activeColour == Piece.BLACK && p.rank(currentSquare) == 7 || p.activeColour == Piece.WHITE && p.rank(currentSquare) == 0) {
                            if (mode == userMode) {
                                Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                                moves.add(m);
                            } else if (mode == aiMode) {
                                Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.QUEEN);
                                moves.add(m);
                                m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.KNIGHT);
                                moves.add(m);
                                m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.ROOK);
                                moves.add(m);
                                m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.BISHOP);
                                moves.add(m);
                            } else {
                                throw new IllegalArgumentException();
                            }

                        } else {
                            Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        }

                    }
                }
            }
        } else if (!Piece.isSliding(originType)) { //kings/knights
            for (int i = 0; i < directions.length; i++) {
                int currentSquare = originSquare + directions[i];
                if (Square.isValid(currentSquare) && p.board[currentSquare] == Piece.NO_PIECE) {
                    Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                    moves.add(m);
                } else if (Square.isValid(currentSquare) && p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                    Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                    moves.add(m);
                }
            }
        } else { //sliding pieces
            for (int i = 0; i < directions.length; i++) {
                int currentSquare = originSquare + directions[i];
                while (Square.isValid(currentSquare) ) {
                    if (p.board[currentSquare] == Piece.NO_PIECE) {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    } else if (p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                        moves.add(m);
                        break;
                    } else if (p.board[currentSquare] != Piece.NO_PIECE) {
                        break;
                    }
                    currentSquare = currentSquare + directions[i];
                }
            }
        }

        //castling
        if (originType == Piece.KING && !p.isChecked(p.activeColour)) {
            if (p.getCastleQueenSide(p.activeColour)) {
                boolean success = true;
                if (p.activeColour == Piece.BLACK) { //check if any pieces between king and queenside rook
                    for (int square = Square.BLACK_QUEENSIDE_ROOK_STARTING_SQUARE + 1; square < Square.BLACK_KING_STARTING_SQUARE; square++) {
                        if (p.board[square] != Piece.NO_PIECE) {
                            success = false;
                            break;
                        }
                    }
                    if (success) {
                        Board copy = p.copy(p);
                        for (Move m: Move.generateBlackQueenSideMoves()) {
                            p.applyMove(m);
                            if (p.isChecked(p.activeColour)) {
                                success = false;
                                break;
                            }
                        }
                        p.restoreBoard(copy);
                        if (success) {
                            Move m = new Move(Move.CASTLE, originSquare, Square.BLACK_KING_STARTING_SQUARE + Square.W + Square.W,
                                    originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        }
                    }
                } else { //check if there are any pieces between the king and queenside rook
                    for (int square = Square.WHITE_QUEENSIDE_ROOK_STARTING_SQUARE + 1; square < Square.WHITE_KING_STARTING_SQUARE; square++) {
                        if (p.board[square] != Piece.NO_PIECE) {
                            success = false;
                            break;
                        }
                    }
                    if (success) {
                        Board copy = p.copy(p);
                        for (Move m: Move.generateWhiteQueenSideMoves()) {
                            p.applyMove(m);
                            if (p.isChecked(p.activeColour)) {

                                success = false;
                                break;
                            }
                        }
                        p.restoreBoard(copy);
                        if (success) {
                            Move m = new Move(Move.CASTLE, originSquare, Square.WHITE_KING_STARTING_SQUARE + Square.W + Square.W,
                                    originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        }
                    }
                }
            }
            if (p.getCastleKingSide(p.activeColour)) {
                boolean success = true;
                if (p.activeColour == Piece.BLACK) {
                    for (int square = Square.BLACK_KING_STARTING_SQUARE + 1; square < Square.BLACK_KINGSIDE_ROOK_STARTING_SQUARE; square++) {
                        if (p.board[square] != Piece.NO_PIECE) {
                            success = false;
                            break;
                        }
                    }
                    if (success) {
                        Board copy = p.copy(p);
                        for (Move m: Move.generateBlackKingSideMoves()) {
                            p.applyMove(m);
                            if (p.isChecked(p.activeColour)) {

                                success = false;
                                break;
                            }
                        }
                        p.restoreBoard(copy);
                        if (success) {
                            Move m = new Move(Move.CASTLE, originSquare, Square.BLACK_KING_STARTING_SQUARE + Square.E + Square.E,
                                    originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);
                        }
                    }
                } else {
                    for (int square = Square.WHITE_KING_STARTING_SQUARE + 1; square < Square.WHITE_KINGSIDE_ROOK_STARTING_SQUARE; square++) {
                        if (p.board[square] != Piece.NO_PIECE) {
                            success = false;
                            break;
                        }
                    }
                    if (success) {
                        Board copy = p.copy(p);
                        for (Move m: Move.generateWhiteKingSideMoves()) {
                            p.applyMove(m);
                            if (p.isChecked(p.activeColour)) {
                                success = false;
                                break;
                            }
                        }
                        p.restoreBoard(copy);
                        if (success) {
                            Move m = new Move(Move.CASTLE, originSquare, Square.WHITE_KING_STARTING_SQUARE + Square.E + Square.E,
                                    originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                            moves.add(m);

                        }
                    }
                }
            }

        }
    }
}
