public class Position {
    public int[] board = new int[128];
 //   public long[][] pieces = new long[2][6]; // first index is colour, second index is piece type
    public int activeColour;

    public Position () {
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
            activeColour = Piece.WHITE;
        } else {
            activeColour = Piece.BLACK;
        }
    }

    /*
        The input move is not fully fleshed out.
        All properties of the move must be tested.
        check if still on board
        check if piece to be moved is same as the current activecolour
        check all directions of the piece
        if encounter any piece along the way, fail
        on arrival, either move piece and/or remove enemy piece
     */
    public boolean makeMove (Move m) {
        int targetSquare = m.getTargetSquare();
        int originSquare = m.getOriginSquare();
        boolean success = false;
        if (!Square.isValid(m.getTargetSquare()) || !Square.isValid(m.getOriginSquare())) {
            return success;
        }

        int originPiece = board[originSquare];
        if (originPiece == Piece.NO_PIECE || Piece.getColour(originPiece) != activeColour) {
            return success;
        }

        int[] directions = Square.getDirection(activeColour, originPiece);

        //start with pawns
        if (Piece.getType(originPiece) == Piece.PAWN) {
            int max = 1;
            if ((activeColour == Piece.BLACK && rank(originSquare) == 1) || (activeColour == Piece.WHITE && rank(originSquare) == 6)) {
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
        }

        int targetPiece = m.getTargetSquare();
        board[targetSquare] = Piece.valueOf(activeColour, originPiece);
        board[originSquare] = Piece.NO_PIECE;

        return success;
    }

    public String serializeBoard() {
        String out = "";
        int fileCount = 0;
        int rankCount = 0;
        for (int index: Square.values) {
            if (fileCount == 8) {
                out = out + "/";
                rankCount++;
                fileCount = 0;
            }
            if (board[toSquare(rankCount, fileCount)] == Piece.NO_PIECE) {
                int count = 1;
                fileCount++;
                for (int i = fileCount; i < 8; i++) {
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

    public int toIndex (int square) {
        return (square + (square & 7)) >> 1;
    }
}











