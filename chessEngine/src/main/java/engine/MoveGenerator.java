package engine;
import java.util.*;

public class MoveGenerator {
    private List<Move> moves;

    public int[] targetSquareToArray() {
        int[] targetSquares = new int[moves.size()];

        int index = 0;
        for (Move m: moves) {
            targetSquares[index] = m.getTargetSquare();
        }
        return targetSquares;
    }

    public MoveGenerator() {
        this.moves = new ArrayList<Move>;
    }

    public void generateMoves(int indexSquare, Position p) {
/*
        int originPiece = p.board[originSquare];
        if (!Piece.isValid(originPiece) || Piece.getColour(originPiece) != p.activeColour) {
            return;
        }

        if (Piece.getType(originPiece) == Piece.PAWN) {
            int max = 1;
            if ((p.activeColour == Piece.BLACK && p.rank(originSquare) == 1) || (p.activeColour == Piece.WHITE && p.rank(originSquare) == 6)) {
                max = 2;
            }
            for (int multiplier = 1; multiplier <= max; multiplier++) {
                int currentSquare = originSquare + directions[0] * multiplier;
                if (currentSquare == targetSquare && board[currentSquare] != Piece.NO_PIECE) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                for (int remainingDirections = 1; remainingDirections < 3; remainingDirections++) {
                    int currentSquare = originSquare + directions[remainingDirections];
                    if ((currentSquare == targetSquare) &&
                            ((board[targetSquare] != Piece.NO_PIECE && Piece.getColour(board[targetSquare]) != activeColour))) {
                        success = true;
                        break;
                    }
                }
            }
        } else if (!Piece.isSliding(Piece.getType(originPiece))) { //kings/knights
            for (int i = 0; i < directions.length; i++) {
                int currentSquare = originSquare + directions[i];
                if ((currentSquare == targetSquare) &&
                        ((board[targetSquare] == Piece.NO_PIECE) || (board[targetSquare] != Piece.NO_PIECE && Piece.getColour(board[targetSquare]) != activeColour))){
                    success = true;
                    break;
                }
            }
        } else { //sliding pieces
            for (int i = 0; i < directions.length; i++) {
                int currentSquare = originSquare + directions[i];
                while (Square.isValid(currentSquare) ) {
                    if ((currentSquare == targetSquare) &&
                            ((board[targetSquare] == Piece.NO_PIECE) || (board[targetSquare] != Piece.NO_PIECE && Piece.getColour(board[targetSquare]) != activeColour))) {
                        success = true;
                        break;
                    } else if (board[currentSquare] != Piece.NO_PIECE) {
                        break;
                    }
                    currentSquare = currentSquare + directions[i];
                }
                if (success) {
                    break;
                }
            }
        }
        if (!success) {
            return success;
        }*/
        return new int[0];
    }

}
