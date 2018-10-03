package engine;
import java.util.*;

public class MoveGenerator {
    private List<Move> moves;

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
    public void generateMoves(Board p) {
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
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    }
                }
                for (int remainingDirections = 1; remainingDirections < 3; remainingDirections++) {
                    int currentSquare = originSquare + directions[remainingDirections];
                    if (Square.isValid(currentSquare) && p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                        moves.add(m);
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
        }
    }


    public void generateMoves(int indexSquare, Board p) {
        int originSquare = Board.toSquare(indexSquare);
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
                    if (p.activeColour == Piece.BLACK && p.rank(originSquare) == 7 || p.activeColour == Piece.WHITE && p.rank(originSquare) == 0) {
                        Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    } else if (multiplier == 2) {
                        Move m = new Move(Move.ENPASSANT_ENABLER, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                    } else {
                        Move m = new Move(Move.NORMAL, originSquare, currentSquare, originPiece, Piece.NO_PIECE, Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    }
                }
            }
            for (int remainingDirections = 1; remainingDirections < 3; remainingDirections++) {
                int currentSquare = originSquare + directions[remainingDirections];
                if (Square.isValid(currentSquare)) {
                    if (currentSquare == p.enPassantSquare && p.board[p.enPassantSquare] == Piece.NO_PIECE) {
                        Move m = new Move(Move.ENPASSANT_CAPTURE, originSquare, currentSquare, originPiece, Piece.valueOf(Piece.oppositeColour(p.activeColour), Piece.PAWN), Piece.NO_PIECE_TYPE);
                        moves.add(m);
                    } else if (p.board[currentSquare] != Piece.NO_PIECE && Piece.getColour(p.board[currentSquare]) != p.activeColour) {
                        if (p.activeColour == Piece.BLACK && p.rank(originSquare) == 7 || p.activeColour == Piece.WHITE && p.rank(originSquare) == 0) {
                            Move m = new Move(Move.PROMOTION, originSquare, currentSquare, originPiece, p.board[currentSquare], Piece.NO_PIECE_TYPE);
                            moves.add(m);
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
        if (originType == Piece.KING) {
            if (!p.isChecked(p.activeColour)) {
                if (p.getCastleLeft(p.activeColour)) {

                }
                if (p.getCastleRight(p.activeColour)) {

                }
            }
            //to do: if rook moves, then that side loses castle right
            //       if king moves, both sides (king and queen side) lose castle right
            //if we're in check, we cannot castle
            //check if there are any pieces between the king and rook to castle
            //check if any of the king's movements leave the king in check
            //
        }

    }

}
