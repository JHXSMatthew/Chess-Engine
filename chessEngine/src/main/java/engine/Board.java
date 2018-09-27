package engine;

public class Board {
    public int[] board = new int[128];
 //   public long[][] pieces = new long[2][6]; // first index is colour, second index is piece type
    public int activeColour;

    public Board() {
        for (int value: Square.values) {
            board[value] = Piece.NO_PIECE;
        }
        activeColour = Piece.WHITE;
    }

    public void deserializeBoard(String state) {
        String[] splitState = state.split(" ");
        String boardRep = splitState[0];
        String colour = splitState[1];

        String[] rows = boardRep.split("/");

        int rank = 0;
        int file;
        for (String s: rows) {
            file = 0;
            for (Character c: s.toCharArray()) {
                Character cleaned = Character.toLowerCase(c);
                if (Character.isDigit(cleaned)) {
                    int numZeroes = Character.getNumericValue(cleaned);
                    for (int i = 0; i < numZeroes; i++) {
                        board[toSquare(rank, file + i)] = Piece.NO_PIECE;
                    }
                    file += numZeroes;
                } else {
                    switch (cleaned) {
                        case 'p':
                            if (Character.isUpperCase(c)) {
                                board[toSquare(rank, file)] = Piece.WHITE_PAWN;
                            } else {
                                board[toSquare(rank, file)] = Piece.BLACK_PAWN;
                            }
                            break;
                        case 'n':
                            if (Character.isUpperCase(c)) {
                                board[toSquare(rank, file)] = Piece.WHITE_KNIGHT;
                            } else {
                                board[toSquare(rank, file)] = Piece.BLACK_KNIGHT;
                            }
                            break;
                        case 'b':
                            if (Character.isUpperCase(c)) {
                                board[toSquare(rank, file)] = Piece.WHITE_BISHOP;
                            } else {
                                board[toSquare(rank, file)] = Piece.BLACK_BISHOP;
                            }
                            break;
                        case 'r':
                            if (Character.isUpperCase(c)) {
                                board[toSquare(rank, file)] = Piece.WHITE_ROOK;
                            } else {
                                board[toSquare(rank, file)] = Piece.BLACK_ROOK;
                            }
                            break;
                        case 'q':
                            if (Character.isUpperCase(c)) {
                                board[toSquare(rank, file)] = Piece.WHITE_QUEEN;
                            } else {
                                board[toSquare(rank, file)] = Piece.BLACK_QUEEN;
                            }
                            break;
                        case 'k':
                            if (Character.isUpperCase(c)) {
                                board[toSquare(rank, file)] = Piece.WHITE_KING;
                            } else {
                                board[toSquare(rank, file)] = Piece.BLACK_KING;
                            }
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    file++;
                }
            }
            rank++;
        }

        if (colour.equals("w")) {
            setColour(Piece.WHITE);
        } else {
            setColour(Piece.BLACK);
        }
    }


    //Move application, everything verified ahead of this function call
    public void applyMove(Move m) {
        int originSquare = m.getOriginSquare();
        int targetSquare = m.getTargetSquare();

        board[targetSquare] = m.getOriginPiece();
        board[originSquare] = Piece.NO_PIECE;
        activeColour = Piece.oppositeColour(activeColour);
    }

    //might be handy?? currently not sure how to implement
    public void undoMove(Move m) {
        int originSquare = m.getOriginSquare();
        int targetSquare = m.getTargetSquare();

        board[targetSquare] = m.getTargetPiece();
        board[originSquare] = m.getOriginPiece();
        activeColour = Piece.oppositeColour(activeColour);
    }
    /*
        basic move evaluation
        isattacked
        ischecked
        ischeckmate
        psuedo legal move generation
        special moves (en passant castle promotion)
        draw conditions
     */

    public boolean validateMove (Move m) {
        int originSquare = m.getOriginSquare();
        int targetSquare = m.getTargetSquare();
        boolean success = false;

        if (!Square.isValid(originSquare) || !Square.isValid(targetSquare)) {
            return success;
        }

        int originPiece = m.getOriginPiece();
        if (originPiece == Piece.NO_PIECE || Piece.getColour(originPiece) != activeColour) {
            return success;
        }

        int originType = Piece.getType(originPiece);

        int[] directions = Square.getDirection(activeColour, originType);

        //start with pawns
        if (originType == Piece.PAWN) {
            int max = 1;
            if ((activeColour == Piece.BLACK && rank(originSquare) == 1) || (activeColour == Piece.WHITE && rank(originSquare) == 6)) {
                max = 2;
            }
            for (int multiplier = 1; multiplier <= max; multiplier++) {
                int currentSquare = originSquare + directions[0] * multiplier;
                if (Square.isValid(currentSquare) && currentSquare == targetSquare && board[currentSquare] == Piece.NO_PIECE) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                for (int remainingDirections = 1; remainingDirections < 3; remainingDirections++) {
                    int currentSquare = originSquare + directions[remainingDirections];
                    if (Square.isValid(currentSquare) && currentSquare == targetSquare &&
                            board[targetSquare] != Piece.NO_PIECE && Piece.getColour(board[targetSquare]) != activeColour) {
                        success = true;
                        break;
                    }
                }
            }
        } else if (!Piece.isSliding(originType)) { //kings/knights
            for (int i = 0; i < directions.length; i++) {
                int currentSquare = originSquare + directions[i];
                if (Square.isValid(currentSquare) && currentSquare == targetSquare &&
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
        return success;
    }

    public boolean isCheckMate(MoveGenerator mg, int colour) {
        int kingSquare = findKing(colour);
        boolean success = false;
        if (kingSquare == Square.NOSQUARE) {
            return success;
        }

        for (Move m: mg.getMoves()) {
            int originPiece = m.getOriginPiece();

            applyMove(m);
            if (Piece.getType(originPiece) == Piece.KING) {
                if (!isChecked(colour)) {
                    success = false;
                } else {
                    success = true;
                }
            } else {
                if (!isChecked(colour, kingSquare)) {
                    success = false;
                } else {
                    success = true;
                }
            }
            undoMove(m);
            if (!success) {
                return success;
            }
        }

        return true;
    }

    public int findKing(int colour) {
        int kingPiece = Piece.valueOf(colour, Piece.KING);
        for (int index: Square.values) {
            if (board[index] == kingPiece) {
                return index;
            }
        }
        return Square.NOSQUARE;
    }

    public boolean isChecked(int colour, int kingSquare) {
        return isAttacked(kingSquare, Piece.oppositeColour(colour));
    }
    public boolean isChecked(int colour) {
        int kingSquare = findKing(colour);
        if (kingSquare == Square.NOSQUARE) {
            return false;
        }
        return isAttacked(kingSquare, Piece.oppositeColour(colour));
    }

    public boolean isAttacked(int targetSquare, int attackerColour) {
        int pawnPiece = Piece.valueOf(attackerColour, Piece.PAWN);
        int[] pawnDirections = Square.getDirection(attackerColour, Piece.PAWN);
        for (int i = 1; i < pawnDirections.length; i++) {
            int attackSquare = targetSquare - pawnDirections[i];
            if (Square.isValid(attackSquare) && board[attackSquare] == pawnPiece) {
                return true;
            }
        }

        int kingPiece = Piece.valueOf(attackerColour, Piece.KING);
        int[] kingDirections = Square.getDirection(attackerColour, Piece.KING);
        for (int i = 0; i < kingDirections.length; i++) {
            int attackSquare = targetSquare + kingDirections[i];
            if (Square.isValid(attackSquare) && board[attackSquare] == kingPiece) {
                return true;
            }
        }
        int knightPiece = Piece.valueOf(attackerColour, Piece.KNIGHT);
        int[] knightDirections = Square.getDirection(attackerColour, Piece.KNIGHT);
        for (int i = 0; i < knightDirections.length; i++) {
            int attackSquare = targetSquare + knightDirections[i];
            if (Square.isValid(attackSquare) && board[attackSquare] == kingPiece) {
                return true;
            }
        }

        int bishopPiece = Piece.valueOf(attackerColour, Piece.BISHOP);
        int queenPiece = Piece.valueOf(attackerColour, Piece.QUEEN); //queens move the same as a bishop and rook
        int[] bishopDirections = Square.getDirection(attackerColour, Piece.BISHOP);
        for (int i = 0; i < bishopDirections.length; i++) {
            int attackSquare = targetSquare + bishopDirections[i];
            while (Square.isValid(attackSquare)) {
                if (Piece.isValid(board[attackSquare]) ) {
                    if (board[attackSquare] == bishopPiece || board[attackSquare] == queenPiece) {
                        return true;
                    }
                    break;
                } else {
                    attackSquare += bishopDirections[i];
                }
            }
        }

        int rookPiece = Piece.valueOf(attackerColour, Piece.ROOK);
        int[] rookDirections = Square.getDirection(attackerColour, Piece.ROOK);
        for (int i = 0; i < rookDirections.length; i++) {
            int attackSquare = targetSquare + rookDirections[i];
            while (Square.isValid(attackSquare)) {
                if (Piece.isValid(board[attackSquare]) ) {
                    if (board[attackSquare] == rookPiece || board[attackSquare] == queenPiece) {
                        return true;
                    }
                    break;
                } else {
                    attackSquare += rookDirections[i];
                }
            }
        }
        return false;
    }

    public String serializeBoard() {
        String out = "";
        int fileCount = 0;
        int rankCount = 0;
        while (rankCount < 8) {
            if (rankCount == 7 && fileCount == 8) {
                break;
            } else if (fileCount == 8) {
                out = out + "/";
                rankCount++;
                fileCount = 0;
            }
            if (board[toSquare(rankCount, fileCount)] == Piece.NO_PIECE) {
                int count = 1;
                fileCount++;
                for (int i = fileCount; i < 8; i++) {
                    if (board[toSquare(rankCount,fileCount)] != Piece.NO_PIECE) {
                        break;
                    }
                    count++;
                    fileCount++;
                }
                out = out + String.valueOf(count);
            } else {
                switch (board[toSquare(rankCount, fileCount)]) {
                    case Piece.WHITE_PAWN:
                        out = out + "P";
                        break;
                    case Piece.BLACK_PAWN:
                        out = out + "p";
                        break;
                    case Piece.WHITE_KNIGHT:
                        out = out + "N";
                        break;
                    case Piece.BLACK_KNIGHT:
                        out = out + "n";
                        break;
                    case Piece.WHITE_BISHOP:
                        out = out + "B";
                        break;
                    case Piece.BLACK_BISHOP:
                        out = out + "b";
                        break;
                    case Piece.WHITE_ROOK:
                        out = out + "R";
                        break;
                    case Piece.BLACK_ROOK:
                        out = out + "r";
                        break;
                    case Piece.WHITE_QUEEN:
                        out = out + "Q";
                        break;
                    case Piece.BLACK_QUEEN:
                        out = out + "q";
                        break;
                    case Piece.WHITE_KING:
                        out = out + "K";
                        break;
                    case Piece.BLACK_KING:
                        out = out + "k";
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                fileCount++;
            }
        }

        out = out + " ";
        if (activeColour == Piece.WHITE) {
            out = out + "w";
        } else {
            out = out + "b";
        }
        return out + " KQkq - 0 1"; //hack because we don't what to do with it yet
    }

    public void setColour (int colour) {
        this.activeColour = colour;
    }

    public static int toSquare (int rank, int file) {
        return 16 * rank + file;
    }

    public int rank(int square) {
        return square >> 4;
    }

    public static int toSquare (int index) {
        return index + (index & ~7);
    }

    public static int toIndex (int square) {
        return (square + (square & 7)) >> 1;
    }
}











