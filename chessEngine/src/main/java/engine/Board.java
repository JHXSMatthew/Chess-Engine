package engine;

import java.util.*;

public class Board {
    public int[] board = new int[128];
 //   public long[][] pieces = new long[2][6]; // first index is colour, second index is piece type
    public int activeColour;
    public int enPassantSquare;

    public boolean whiteQueenCastle;
    public boolean whiteKingCastle;

    public boolean blackQueenCastle;
    public boolean blackKingCastle;

    public Board() {
        for (int value: Square.values) {
            board[value] = Piece.NO_PIECE;
        }
        activeColour = Piece.WHITE;
        enPassantSquare = Square.NOSQUARE;
        whiteQueenCastle = false;
        whiteKingCastle = false;
        blackQueenCastle = false;
        blackKingCastle = false;
    }

    public Board copy(Board b) {
        Board copy = new Board();
        for (int value: Square.values) {
            copy.board[value] = b.board[value];
        }
        copy.activeColour = b.activeColour;
        copy.enPassantSquare = b.enPassantSquare;
        copy.whiteQueenCastle = b.whiteQueenCastle;
        copy.whiteKingCastle = b.whiteKingCastle;
        copy.blackQueenCastle = b.blackQueenCastle;
        copy.blackKingCastle = b.blackKingCastle;

        return copy;
    }

    public void deserializeBoard(String state) {
        String[] splitState = state.split(" ");
        String boardRep = splitState[0];
        String colour = splitState[1];
        String castleRights = splitState[2];
        String enPassant = splitState[3];

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

        if (!castleRights.equals("-")) {
            char[] castleCharacters = castleRights.toCharArray();
            for (Character c: castleCharacters) {
                if (c.equals('k')) {
                    blackKingCastle = true;
                } else if (c.equals('K')) {
                    whiteKingCastle = true;
                } else if (c.equals('q')) {
                    blackQueenCastle = true;
                } else if (c.equals('Q')) {
                    whiteQueenCastle = true;
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
        if (board[Square.BLACK_KING_STARTING_SQUARE] != Piece.BLACK_KING) {
            blackKingCastle = false;
            blackQueenCastle = false;
        } else if (board[Square.WHITE_KING_STARTING_SQUARE] != Piece.WHITE_KING) {
            whiteKingCastle = false;
            whiteQueenCastle = false;
        } else if (blackKingCastle) {
            if (board[Square.BLACK_KINGSIDE_ROOK_STARTING_SQUARE] != Piece.BLACK_ROOK) {
                blackKingCastle = false;
            }
        } else if (whiteKingCastle) {
            if (board[Square.WHITE_KINGSIDE_ROOK_STARTING_SQUARE] != Piece.WHITE_ROOK) {
                whiteKingCastle = false;
            }
        } else if (blackQueenCastle) {
            if (board[Square.BLACK_QUEENSIDE_ROOK_STARTING_SQUARE] != Piece.BLACK_ROOK) {
                blackQueenCastle = false;
            }
        } else if (whiteQueenCastle) {
            if (board[Square.WHITE_QUEENSIDE_ROOK_STARTING_SQUARE] != Piece.WHITE_ROOK) {
                whiteQueenCastle = false;
            }
        }

        if (!enPassant.equals("-")) {
            int square = toSquare(Integer.parseInt(enPassant));
            if (Square.isValid(square)) {
                enPassantSquare = square;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }


    //Move application, everything verified ahead of this function call
    public void applyMove(Move m) {
        int originSquare = m.getOriginSquare();
        int targetSquare = m.getTargetSquare();
        int type = m.getType();
        if (type == Move.ENPASSANT_ENABLER) {
            board[targetSquare] = m.getOriginPiece();
            board[originSquare] = Piece.NO_PIECE;
            activeColour = Piece.oppositeColour(activeColour);

            if (Piece.getColour(m.getOriginPiece()) == Piece.WHITE) {
                enPassantSquare = m.getTargetSquare() + Square.N;
            } else if (Piece.getColour(m.getOriginPiece()) == Piece.BLACK) {
                enPassantSquare = m.getTargetSquare() + Square.S;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            enPassantSquare = Square.NOSQUARE;
             if (type == Move.ENPASSANT_CAPTURE) {
                board[targetSquare] = m.getOriginPiece();
                board[originSquare] = Piece.NO_PIECE;

                if (Piece.getColour(m.getOriginPiece()) == Piece.WHITE) {
                    board[targetSquare + Square.S] = Piece.NO_PIECE;
                } else if (Piece.getColour(m.getOriginPiece()) == Piece.BLACK) {
                    board[targetSquare + Square.N] = Piece.NO_PIECE;
                } else {
                    throw new IllegalArgumentException();
                }

                activeColour = Piece.oppositeColour(activeColour);
            } else { //else if (m.getType() == Move.NORMAL) {
                 board[targetSquare] = m.getOriginPiece();
                 board[originSquare] = Piece.NO_PIECE;
                 activeColour = Piece.oppositeColour(activeColour);
             }

        }
        int type = m.getType();
        if (type == Move.ENPASSANT_ENABLER) {
            board[targetSquare] = m.getOriginPiece();
            board[originSquare] = Piece.NO_PIECE;
            activeColour = Piece.oppositeColour(activeColour);

            if (Piece.getColour(m.getOriginPiece()) == Piece.WHITE) {
                enPassantSquare = m.getTargetSquare() + Square.N;
            } else if (Piece.getColour(m.getOriginPiece()) == Piece.BLACK) {
                enPassantSquare = m.getTargetSquare() + Square.S;
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            enPassantSquare = Square.NOSQUARE;
            if (type == Move.ENPASSANT_CAPTURE) {
                board[targetSquare] = m.getOriginPiece();
                board[originSquare] = Piece.NO_PIECE;

                if (Piece.getColour(m.getOriginPiece()) == Piece.WHITE) {
                    board[targetSquare + Square.S] = Piece.NO_PIECE;
                } else if (Piece.getColour(m.getOriginPiece()) == Piece.BLACK) {
                    board[targetSquare + Square.N] = Piece.NO_PIECE;
                } else {
                    throw new IllegalArgumentException();
                }

            } else if (type == Move.CASTLE) {
                board[targetSquare] = m.getOriginPiece();
                board[originSquare] = Piece.NO_PIECE;

                if (board[targetSquare] == Square.BLACK_KINGSIDE_FINISHING_SQUARE) {
                    board[Square.BLACK_KINGSIDE_ROOK_STARTING_SQUARE] = Piece.NO_PIECE;
                    board[Square.BLACK_KINGSIDE_FINISHING_SQUARE + Square.W] = Piece.valueOf(activeColour, Piece.ROOK);
                } else if (board[targetSquare] == Square.BLACK_QUEENSIDE_FINISHING_SQUARE) {
                    board[Square.BLACK_QUEENSIDE_ROOK_STARTING_SQUARE] = Piece.NO_PIECE;
                    board[Square.BLACK_QUEENSIDE_FINISHING_SQUARE + Square.E] = Piece.valueOf(activeColour, Piece.ROOK);
                } else if (board[targetSquare] == Square.WHITE_KINGSIDE_FINISHING_SQUARE) {
                    board[Square.WHITE_KINGSIDE_ROOK_STARTING_SQUARE] = Piece.NO_PIECE;
                    board[Square.WHITE_KINGSIDE_FINISHING_SQUARE + Square.W] = Piece.valueOf(activeColour, Piece.ROOK);
                } else if (board[targetSquare] == Square.WHITE_QUEENSIDE_FINISHING_SQUARE) {
                    board[Square.WHITE_QUEENSIDE_ROOK_STARTING_SQUARE] = Piece.NO_PIECE;
                    board[Square.WHITE_QUEENSIDE_FINISHING_SQUARE + Square.E] = Piece.valueOf(activeColour, Piece.ROOK);
                } else {
                    throw new IllegalArgumentException();
                }

                removeCastleKingSide(activeColour);
                removeCastleQueenSide(activeColour);
            } else { //else if (m.getType() == Move.NORMAL) {
                int pieceType = Piece.getType(m.getOriginPiece());
                if (pieceType == Piece.KING) {
                    removeCastleKingSide(activeColour);
                    removeCastleQueenSide(activeColour);
                } else if (pieceType == Piece.ROOK) {
                    if (originSquare == Square.WHITE_KINGSIDE_ROOK_STARTING_SQUARE) {
                        removeCastleKingSide(activeColour);
                    } else if (originSquare == Square.WHITE_QUEENSIDE_ROOK_STARTING_SQUARE) {
                        removeCastleQueenSide(activeColour);
                    } else if (originSquare == Square.BLACK_QUEENSIDE_ROOK_STARTING_SQUARE) {
                        removeCastleQueenSide(activeColour);
                    } else if (originSquare == Square.BLACK_KINGSIDE_ROOK_STARTING_SQUARE) {
                        removeCastleKingSide(activeColour);
                    }
                }

                board[targetSquare] = m.getOriginPiece();
                board[originSquare] = Piece.NO_PIECE;
            }
            activeColour = Piece.oppositeColour(activeColour);
        }

    }

    //everything verified ahead of this call. currently only used for isCheckMate
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

    public State completePromotion(String stateString, int index, int promotionPieceType) {
        int square = toSquare(index);

        State boardRep = new State();
        boardRep.setBoardRep(stateString);

        if (Square.isValid(square) && promotionPieceType != Piece.NO_PIECE_TYPE && promotionPieceType != Piece.PAWN) {
            int promotionPiece = Piece.valueOf(Piece.oppositeColour(activeColour), promotionPieceType);
            board[square] = promotionPiece;

            //does the move check the other player
            if (isChecked(activeColour)) {

                //does the move checkmate the other player?
                boardRep.setCheck(true);
                MoveGenerator mg = new MoveGenerator();
                mg.generateMoves(this);
                boardRep.setCheckMate(isCheckMate(mg, activeColour));
            }


            boardRep.setBoardRep(serializeBoard());
        }
        return boardRep;
    }

    //will need to implement check statemate

    public State psuedoLegalMakeMove(String stateString, Move m) {
        //is the move valid
        State boardRep = new State();
        boardRep.setBoardRep(stateString);

        MoveGenerator mg = new MoveGenerator();
        mg.generateMoves(m.getOriginSquare(), this);

        Move match = new Move();
        for (Move moves: mg.getMoves()) {
            if (moves.getTargetSquare() == m.getTargetSquare()) {
                match = moves;
                break;
            }
        }

        if (match.getType() != Move.EMPTY) {

            //lets apply the move
            Board copy = copy(this);
            applyMove(match);

            //no move is allowed to leave us in check
            if (isChecked(Piece.oppositeColour(activeColour))) {

                restoreBoard(copy);
                if (isChecked(activeColour)) {
                    boardRep.setCheck(true);
                    MoveGenerator checkMateMoves = new MoveGenerator();
                    checkMateMoves.generateMoves(this);
                    boardRep.setCheckMate(isCheckMate(checkMateMoves, activeColour));
                }

                boardRep.setBoardRep(serializeBoard());
                return boardRep;
            }

            //if the move is a promotion, we first must get user input for the type of promotion Piece, then we check if isCheck and isCheckMate
            if (match.getType() == Move.PROMOTION) {
                boardRep.setPromotion(true);
            //does the move check the other player
            } else if (isChecked(activeColour)) {

                boardRep.setCheck(true);
                //does the move checkmate the other player?
                MoveGenerator checkMateMoves = new MoveGenerator();
                checkMateMoves.generateMoves(this);
                boardRep.setCheckMate(isCheckMate(checkMateMoves, activeColour));
            }
        }
        boardRep.setBoardRep(serializeBoard());
        return boardRep;
    }

    public boolean validateMove (Move m) {
        int originSquare = m.getOriginSquare();
        int targetSquare = m.getTargetSquare();
        boolean success = false;

        if (!Square.isValid(originSquare) || !Square.isValid(targetSquare)) {
            return success;
        }

        int originPiece = m.getOriginPiece();
        if (!Piece.isValid(originPiece)) {
            return success;
        }
        if (Piece.getColour(originPiece) != activeColour) {
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

    public boolean arrayContains(int[] arr, int val) {
        for (int num: arr) {
            if (num == val) {
                return true;
            }
        }
        return false;
    }

    public boolean isCheckMate(MoveGenerator mg, int colour) {
        int kingSquare = findKing(colour);
        Board copy = copy(this);

        boolean success = true;
        if (kingSquare == Square.NOSQUARE) {
            throw new IllegalArgumentException();
        }
        for (Move m: mg.getMoves()) {
            int originPiece = m.getOriginPiece();
            applyMove(m);
            if (Piece.getType(originPiece) == Piece.KING) {
                if (!isChecked(colour)) {
                    success = false;
                }
            } else {
                if (!isChecked(colour, kingSquare)) {
                    success = false;
                }
            }

            restoreBoard(copy);
            //undoMove(m);
            if (!success) {
                return success;
            }
        }

        return true;
    }

    public void restoreBoard(Board copy) {
        for (int values: Square.values) {
            board[values] = copy.board[values];
        }
        activeColour = copy.activeColour;
        enPassantSquare = copy.enPassantSquare;
        whiteQueenCastle = copy.whiteQueenCastle;
        whiteKingCastle = copy.whiteKingCastle;
        blackQueenCastle = copy.blackQueenCastle;
        blackKingCastle = copy.blackKingCastle;
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
            if (Square.isValid(attackSquare) && board[attackSquare] == knightPiece) {
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
        out = out + " ";
        if (whiteKingCastle) {
            out = out + "K";
        }
        if (whiteQueenCastle) {
            out = out + "Q";
        }
        if (blackKingCastle) {
            out = out + "k";
        }
        if (blackQueenCastle) {
            out = out + "q";
        }

        out = out + " ";
        if (enPassantSquare == Square.NOSQUARE) {
            out = out + "-";
        } else {
            out = out + String.valueOf(enPassantSquare);
        }
        return out + " 0 1"; //hack because we don't what to do with it yet
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

    public boolean getCastleKingSide(int colour) {
        if (colour == Piece.WHITE) {
            return whiteKingCastle;
        } else if (colour == Piece.BLACK) {
            return blackKingCastle;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean getCastleQueenSide(int colour) {
        if (colour == Piece.WHITE) {
            return whiteQueenCastle;
        } else if (colour == Piece.BLACK) {
            return blackQueenCastle;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public void removeCastleKingSide(int colour) {
        if (colour == Piece.WHITE) {
            whiteKingCastle = false;
        } else if (colour == Piece.BLACK) {
            blackKingCastle = false;
        } else {
            throw new IllegalArgumentException();
        }
    }
    public void removeCastleQueenSide(int colour) {
        if (colour == Piece.WHITE) {
            whiteQueenCastle = false;
        } else if (colour == Piece.BLACK) {
            blackQueenCastle = false;
        } else {
            throw new IllegalArgumentException();
        }
    }

}











