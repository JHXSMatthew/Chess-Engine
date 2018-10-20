package engine;

import java.util.*;

public class Piece {
    static final int WHITE_PAWN = 0;
    static final int WHITE_KNIGHT = 1;
    static final int WHITE_BISHOP = 2;
    static final int WHITE_ROOK = 3;
    static final int WHITE_QUEEN = 4;
    static final int WHITE_KING = 5;
    static final int BLACK_PAWN = 6;
    static final int BLACK_KNIGHT = 7;
    static final int BLACK_BISHOP = 8;
    static final int BLACK_ROOK = 9;
    static final int BLACK_QUEEN = 10;
    static final int BLACK_KING = 11;
    static final int NO_PIECE = 12;

    static final int PAWN = 0;
    static final int KNIGHT = 1;
    static final int BISHOP = 2;
    static final int ROOK = 3;
    static final int QUEEN = 4;
    static final int KING = 5;

    static final int NO_PIECE_TYPE = 6;

    static final int PAWN_VALUE = 100;
    static final int KNIGHT_VALUE = 325;
    static final int BISHOP_VALUE = 325;
    static final int ROOK_VALUE = 500;
    static final int QUEEN_VALUE = 975;
    static final int KING_VALUE = 20000;

    static final int WHITE = 0;
    static final int BLACK = 1;

    static int oppositeColour (int colour) {
        switch (colour) {
            case WHITE:
                return BLACK;
            case BLACK:
                return WHITE;
            default:
                throw new IllegalArgumentException();

        }
    }

    static int getValue(int piece) {
        switch (piece) {
            case WHITE_PAWN:
            case BLACK_PAWN:
                return PAWN_VALUE;
            case WHITE_KNIGHT:
            case BLACK_KNIGHT:
                return KNIGHT_VALUE;
            case WHITE_BISHOP:
            case BLACK_BISHOP:
                return BISHOP_VALUE;
            case WHITE_ROOK:
            case BLACK_ROOK:
                return ROOK_VALUE;
            case WHITE_QUEEN:
            case BLACK_QUEEN:
                return QUEEN_VALUE;
            case WHITE_KING:
            case BLACK_KING:
                return KING_VALUE;
            default:
                throw new IllegalArgumentException();
        }
    }

    static boolean isSliding(int pieceType) {
        switch (pieceType) {
            case PAWN:
            case KNIGHT:
            case KING:
                return false;
            case BISHOP:
            case ROOK:
            case QUEEN:
                return true;
            default:
                throw new IllegalArgumentException();
        }
    }

    static boolean isValid(int piece) {
        switch (piece) {
            case WHITE_PAWN:
            case WHITE_KNIGHT:
            case WHITE_BISHOP:
            case WHITE_ROOK:
            case WHITE_QUEEN:
            case WHITE_KING:
            case BLACK_PAWN:
            case BLACK_KNIGHT:
            case BLACK_BISHOP:
            case BLACK_ROOK:
            case BLACK_QUEEN:
            case BLACK_KING:
                return true;
            default:
                return false;
        }
    }

    static int valueOf(int colour, int pieceType) {
        switch (colour) {
            case WHITE:
                switch (pieceType) {
                    case PAWN:
                        return WHITE_PAWN;
                    case KNIGHT:
                        return WHITE_KNIGHT;
                    case BISHOP:
                        return WHITE_BISHOP;
                    case ROOK:
                        return WHITE_ROOK;
                    case QUEEN:
                        return WHITE_QUEEN;
                    case KING:
                        return WHITE_KING;
                    default:
                        throw new IllegalArgumentException();
                }
            case BLACK:
                switch (pieceType) {
                    case PAWN:
                        return BLACK_PAWN;
                    case KNIGHT:
                        return BLACK_KNIGHT;
                    case BISHOP:
                        return BLACK_BISHOP;
                    case ROOK:
                        return BLACK_ROOK;
                    case QUEEN:
                        return BLACK_QUEEN;
                    case KING:
                        return BLACK_KING;
                    default:
                        throw new IllegalArgumentException();
                }
            default:
                throw new IllegalArgumentException();
        }
    }

    static int getType(int piece) {
        switch (piece) {
            case WHITE_PAWN:
            case BLACK_PAWN:
                return PAWN;
            case WHITE_KNIGHT:
            case BLACK_KNIGHT:
                return KNIGHT;
            case WHITE_BISHOP:
            case BLACK_BISHOP:
                return BISHOP;
            case WHITE_ROOK:
            case BLACK_ROOK:
                return ROOK;
            case WHITE_QUEEN:
            case BLACK_QUEEN:
                return QUEEN;
            case WHITE_KING:
            case BLACK_KING:
                return KING;
            default:
                throw new IllegalArgumentException();
        }
    }

    static int getColour(int piece) {
        switch (piece) {
            case WHITE_PAWN:
            case WHITE_KNIGHT:
            case WHITE_BISHOP:
            case WHITE_ROOK:
            case WHITE_QUEEN:
            case WHITE_KING:
                return WHITE;
            case BLACK_PAWN:
            case BLACK_KNIGHT:
            case BLACK_BISHOP:
            case BLACK_ROOK:
            case BLACK_QUEEN:
            case BLACK_KING:
                return BLACK;
            default:
                throw new IllegalArgumentException();
        }
    }





}
