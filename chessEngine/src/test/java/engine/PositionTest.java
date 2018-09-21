package engine;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JHXSMatthew on 17/9/18.
 */
public class PositionTest {

    @Test
    public void de(){
        ChessEngineDummy engine = new ChessEngineDummy();

        Board p = new Board();
        p.deserializeBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Assert.assertEquals(p.activeColour, Piece.WHITE);
        Assert.assertEquals(p.board[0], Piece.BLACK_ROOK);
        Assert.assertEquals(p.board[1], Piece.BLACK_KNIGHT);
        Assert.assertEquals(p.board[2], Piece.BLACK_BISHOP);
        Assert.assertEquals(p.board[3], Piece.BLACK_QUEEN);
        Assert.assertEquals(p.board[4], Piece.BLACK_KING);
/*
        MoveGenerator g = new MoveGenerator();
        Board b = new Board();
        b.deserializeBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");

        g.generateMoves(8, b);
        int[] pawnMoves = g.targetSquareToIndexArray();
        System.out.println(pawnMoves[0] + " " + pawnMoves[1]);
       // Assert.assertEquals(g.targetSquareToIndexArray(), new int[]{16,24});
        g.emptyMoves();
        g.generateMoves(1, b);
        int[] knightMoves = g.targetSquareToIndexArray();
        System.out.println(knightMoves[0] + " " + knightMoves[1]);
        Assert.assertEquals(g.targetSquareToIndexArray(), new int[]{18, 16});
        g.emptyMoves();*/

        //Rook tests
        String rookMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 16);
        Assert.assertEquals(rookMove, "1nbqkbnr/8/r7/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        String rookMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 56);
        Assert.assertEquals(rookMove1, "1nbqkbnr/8/8/8/8/8/8/rNBQKBNR w KQkq - 0 1");
        String rookMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 5); //blocked by own piece
        Assert.assertEquals(rookMove2, "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        String rookMove3 = engine.move("rnbqkbnr/8/8/8/8/8/Q7/RNB1KBNR b KQkq - 0 1", 0, 56); //blocked by opponent piece
        Assert.assertEquals(rookMove3, "rnbqkbnr/8/8/8/8/8/Q7/RNB1KBNR b KQkq - 0 1");

        //Knight tests
        String knightMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 1, 11);
        Assert.assertEquals(knightMove, "r1bqkbnr/3n4/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        String knightMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 57, 40);
        Assert.assertEquals(knightMove1, "rnbqkbnr/8/8/8/8/N7/8/R1BQKBNR b KQkq - 0 1");
        String knightMove2 = engine.move("r1bqkbnr/8/1n6/8/8/8/8/RNBQKBNR b KQkq - 0 1", 17, 0); //blocked by own piece
        Assert.assertEquals(knightMove2, "r1bqkbnr/8/1n6/8/8/8/8/RNBQKBNR b KQkq - 0 1");

        //Bishop tests
        String bishopMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 2, 16);
        Assert.assertEquals(bishopMove, "rn1qkbnr/8/b7/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        String bishopMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 2, 47);
        Assert.assertEquals(bishopMove2, "rn1qkbnr/8/8/8/8/7b/8/RNBQKBNR w KQkq - 0 1");
        String bishopMove3 = engine.move("rnbqkbnr/3p4/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 2, 47); //blocked by own piece
        Assert.assertEquals(bishopMove3, "rnbqkbnr/3p4/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        String bishopMove4 = engine.move("rnbqkbnr/8/8/8/6Q1/8/8/RNB1KBNR b KQkq - 0 1", 2, 47); //blocked by opponent piece
        Assert.assertEquals(bishopMove4, "rnbqkbnr/8/8/8/6Q1/8/8/RNB1KBNR b KQkq - 0 1");

        //Queen tests
        String queenMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 24);
        Assert.assertEquals(queenMove, "rnb1kbnr/8/8/q7/8/8/8/RNBQKBNR w KQkq - 0 1");
        String queenMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 30);
        Assert.assertEquals(queenMove2, "rnb1kbnr/8/8/6q1/8/8/8/RNBQKBNR w KQkq - 0 1");
        String queenMove3 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 43);
        Assert.assertEquals(queenMove3, "rnb1kbnr/8/8/8/8/3q4/8/RNBQKBNR w KQkq - 0 1");
        String queenMove4 = engine.move("rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 39); //blocked by own piece
        Assert.assertEquals(queenMove4, "rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        String queenMove5 = engine.move("rnbqkbnr/8/8/8/3N4/8/8/R1BQKBNR w KQkq - 0 1", 3, 59); //blocked by opponent piece
        Assert.assertEquals(queenMove5, "rnbqkbnr/8/8/8/3N4/8/8/R1BQKBNR w KQkq - 0 1");

        //King tests
        String kingMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 11);
        Assert.assertEquals(kingMove, "rnbq1bnr/3k4/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        String kingMove2 = engine.move("rnbq1bnr/5k2/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 13, 21);
        Assert.assertEquals(kingMove2, "rnbq1bnr/8/5k2/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        String kingMove3 = engine.move("rnbq1bnr/8/4k3/8/8/8/8/RNBQKBNR b KQkq - 0 1", 20, 28);
        Assert.assertEquals(kingMove3, "rnbq1bnr/8/8/4k3/8/8/8/RNBQKBNR w KQkq - 0 1");
        String kingMove4 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 3); //blocked by own piece
        Assert.assertEquals(kingMove4, "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        String kingMove5 = engine.move("rnbq1bnr/4k3/8/8/8/8/8/R1BQKBNR w KQkq - 0 1", 12, 5); //blocked by own piece
        Assert.assertEquals(kingMove5, "rnbq1bnr/4k3/8/8/8/8/8/R1BQKBNR w KQkq - 0 1");

        //Pawn tests
        String pawnMove = engine.move("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 53, 37);
        Assert.assertEquals(pawnMove, "rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq - 0 1");
        String pawnMove2 = engine.move("rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 13, 29);
        Assert.assertEquals(pawnMove2, "rnbqkbnr/ppppp1pp/8/5p2/5P2/8/PPPPP1PP/RNBQKBNR w KQkq - 0 1");
        String pawnMove3 = engine.move("rnbqkbnr/ppppp1pp/8/5p2/5P2/8/PPPPP1PP/RNBQKBNR w KQkq - 0 1", 54, 46);
        Assert.assertEquals(pawnMove3, "rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP2P/RNBQKBNR b KQkq - 0 1");
        String pawnMove4 = engine.move("rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 29, 21); //pawns can't move backwards
        Assert.assertEquals(pawnMove4, "rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1");
        String pawnMove5 = engine.move("rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 29, 37); //blocked by opponent piece
        Assert.assertEquals(pawnMove5, "rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1");

        //Check pawn attack
        String pawnMove6 = engine.move("rnbqkbnr/ppp1pppp/8/3p4/4P3/3P4/PPP2PPP/RNBQKBNR b KQkq - 0 1", 27, 36);
        Assert.assertEquals(pawnMove6, "rnbqkbnr/ppp1pppp/8/8/4p3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 1");
        String pawnMove7 = engine.move("rnbqkbnr/ppp1pppp/8/8/4p3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 1", 43, 36);
        Assert.assertEquals(pawnMove7, "rnbqkbnr/ppp1pppp/8/8/4P3/8/PPP2PPP/RNBQKBNR b KQkq - 0 1");

        //Check checkmate
        String checkmateMove = engine.move("k7/8/6r1/8/8/4bq2/7K/8 b KQkq - 0 1", 44, 37);
        Assert.assertEquals(checkmateMove, "k7/8/6r1/8/5b2/5q2/7K/8 w KQkq - 0 1");
        String checkmateMove2 = engine.move("8/8/1k6/8/8/2R5/7R/5K2 w KQkq - 0 1", 55, 49);
        Assert.assertEquals(checkmateMove2, "8/8/1k6/8/8/2R5/1R6/5K2 b KQkq - 0 1");
        String checkmateMove3 = engine.move("2q5/8/7k/4n3/7K/1r6/8/8 b KQkq - 0 1", 2, 3);
        Assert.assertEquals(checkmateMove3, "3q4/8/7k/4n3/7K/1r6/8/8 w KQkq - 0 1");

        //Check checks
        String checkMove = engine.move("8/6k1/8/4b3/8/K7/8/3q4 b KQkq - 0 1", 28, 19);
        Assert.assertEquals(checkMove, "8/6k1/3b4/8/8/K7/8/3q4 w KQkq - 0 1");
        String checkMove2 = engine.move("8/2k5/6k1/8/8/3Q4/7K/r7 w KQkq - 0 1", 43, 42);
        Assert.assertEquals(checkMove2, "8/2k5/6k1/8/8/2Q5/7K/r7 b KQkq - 0 1");
        String checkMove3 = engine.move("2n5/k3p3/5Q2/1p6/p6P/P3b3/8/5K2 w KQkq - 0 1", 21, 12);
        Assert.assertEquals(checkMove3, "2n5/k3Q3/8/1p6/p6P/P3b3/8/5K2 b KQkq - 0 1");


    }
}
