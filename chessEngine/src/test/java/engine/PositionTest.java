package engine;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JHXSMatthew on 17/9/18.
 */
public class PositionTest {

    @Test
    public void moveTest(){
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
        State rookMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 16);
        Assert.assertEquals(rookMove.getBoardRep(), "1nbqkbnr/8/r7/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        State rookMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 56);
        Assert.assertEquals(rookMove1.getBoardRep(), "1nbqkbnr/8/8/8/8/8/8/rNBQKBNR w KQkq - 0 1");
        State rookMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 5); //blocked by own piece
        Assert.assertEquals(rookMove2.getBoardRep(), "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        State rookMove3 = engine.move("rnbqkbnr/8/8/8/8/8/Q7/RNB1KBNR b KQkq - 0 1", 0, 56); //blocked by opponent piece
        Assert.assertEquals(rookMove3.getBoardRep(), "rnbqkbnr/8/8/8/8/8/Q7/RNB1KBNR b KQkq - 0 1");

        //Knight tests
        State knightMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 1, 11);
        Assert.assertEquals(knightMove.getBoardRep(), "r1bqkbnr/3n4/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        State knightMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 57, 40);
        Assert.assertEquals(knightMove1.getBoardRep(), "rnbqkbnr/8/8/8/8/N7/8/R1BQKBNR b KQkq - 0 1");
        State knightMove2 = engine.move("r1bqkbnr/8/1n6/8/8/8/8/RNBQKBNR b KQkq - 0 1", 17, 0); //blocked by own piece
        Assert.assertEquals(knightMove2.getBoardRep(), "r1bqkbnr/8/1n6/8/8/8/8/RNBQKBNR b KQkq - 0 1");

        //Bishop tests
        State bishopMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 2, 16);
        Assert.assertEquals(bishopMove.getBoardRep(), "rn1qkbnr/8/b7/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        State bishopMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 2, 47);
        Assert.assertEquals(bishopMove2.getBoardRep(), "rn1qkbnr/8/8/8/8/7b/8/RNBQKBNR w KQkq - 0 1");
        State bishopMove3 = engine.move("rnbqkbnr/3p4/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 2, 47); //blocked by own piece
        Assert.assertEquals(bishopMove3.getBoardRep(), "rnbqkbnr/3p4/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        State bishopMove4 = engine.move("rnbqkbnr/8/8/8/6Q1/8/8/RNB1KBNR b KQkq - 0 1", 2, 47); //blocked by opponent piece
        Assert.assertEquals(bishopMove4.getBoardRep(), "rnbqkbnr/8/8/8/6Q1/8/8/RNB1KBNR b KQkq - 0 1");

        //Queen tests
        State queenMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 24);
        Assert.assertEquals(queenMove.getBoardRep(), "rnb1kbnr/8/8/q7/8/8/8/RNBQKBNR w KQkq - 0 1");
        State queenMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 30);
        Assert.assertEquals(queenMove2.getBoardRep(), "rnb1kbnr/8/8/6q1/8/8/8/RNBQKBNR w KQkq - 0 1");
        State queenMove3 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 43);
        Assert.assertEquals(queenMove3.getBoardRep(), "rnb1kbnr/8/8/8/8/3q4/8/RNBQKBNR w KQkq - 0 1");
        State queenMove4 = engine.move("rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 3, 39); //blocked by own piece
        Assert.assertEquals(queenMove4.getBoardRep(), "rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        State queenMove5 = engine.move("rnbqkbnr/8/8/8/3N4/8/8/R1BQKBNR w KQkq - 0 1", 3, 59); //blocked by opponent piece
        Assert.assertEquals(queenMove5.getBoardRep(), "rnbqkbnr/8/8/8/3N4/8/8/R1BQKBNR w KQkq - 0 1");

        //King tests
        State kingMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 12);
        Assert.assertEquals(kingMove.getBoardRep(), "rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        State kingMove2 = engine.move("rnbq1bnr/5k2/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 13, 20);
        Assert.assertEquals(kingMove2.getBoardRep(), "rnbq1bnr/8/4k3/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        State kingMove3 = engine.move("rnbq1bnr/8/4k3/8/8/8/8/RNBQKBNR b KQkq - 0 1", 20, 28);
        Assert.assertEquals(kingMove3.getBoardRep(), "rnbq1bnr/8/8/4k3/8/8/8/RNBQKBNR w KQkq - 0 1");
        State kingMove4 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 3); //blocked by own piece
        Assert.assertEquals(kingMove4.getBoardRep(), "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        State kingMove5 = engine.move("rnbq1bnr/4k3/8/8/8/8/8/R1BQKBNR w KQkq - 0 1", 12, 5); //blocked by own piece
        Assert.assertEquals(kingMove5.getBoardRep(), "rnbq1bnr/4k3/8/8/8/8/8/R1BQKBNR w KQkq - 0 1");

        //Pawn tests
        State pawnMove = engine.move("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 53, 37);
        Assert.assertEquals(pawnMove.getBoardRep(), "rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq - 0 1");
        State pawnMove2 = engine.move("rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 13, 29);
        Assert.assertEquals(pawnMove2.getBoardRep(), "rnbqkbnr/ppppp1pp/8/5p2/5P2/8/PPPPP1PP/RNBQKBNR w KQkq - 0 1");
        State pawnMove3 = engine.move("rnbqkbnr/ppppp1pp/8/5p2/5P2/8/PPPPP1PP/RNBQKBNR w KQkq - 0 1", 54, 46);
        Assert.assertEquals(pawnMove3.getBoardRep(), "rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP2P/RNBQKBNR b KQkq - 0 1");
        State pawnMove4 = engine.move("rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 29, 21); //pawns can't move backwards
        Assert.assertEquals(pawnMove4.getBoardRep(), "rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1");
        State pawnMove5 = engine.move("rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 29, 37); //blocked by opponent piece
        Assert.assertEquals(pawnMove5.getBoardRep(), "rnbqkbnr/ppppp1pp/8/5p2/5P2/6P1/PPPPP1PP/RNBQKBNR b KQkq - 0 1");

        //Check pawn attack
        State pawnMove6 = engine.move("rnbqkbnr/ppp1pppp/8/3p4/4P3/3P4/PPP2PPP/RNBQKBNR b KQkq - 0 1", 27, 36);
        Assert.assertEquals(pawnMove6.getBoardRep(), "rnbqkbnr/ppp1pppp/8/8/4p3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 1");
        State pawnMove7 = engine.move("rnbqkbnr/ppp1pppp/8/8/4p3/3P4/PPP2PPP/RNBQKBNR w KQkq - 0 1", 43, 36);
        Assert.assertEquals(pawnMove7.getBoardRep(), "rnbqkbnr/ppp1pppp/8/8/4P3/8/PPP2PPP/RNBQKBNR b KQkq - 0 1");

        //Check checkmate
        State checkmateMove = engine.move("k7/8/6r1/8/8/4bq2/7K/8 b KQkq - 0 1", 44, 37);
        Assert.assertEquals(checkmateMove.getBoardRep(), "k7/8/6r1/8/5b2/5q2/7K/8 w KQkq - 0 1");
        Assert.assertEquals(checkmateMove.isCheck(), true);
        Assert.assertEquals(checkmateMove.isCheckMate(), true);
        State checkmateMove2 = engine.move("8/8/k7/8/8/1R6/7R/5K2 w KQkq - 0 1", 55, 48);
        Assert.assertEquals(checkmateMove2.getBoardRep(), "8/8/k7/8/8/1R6/R7/5K2 b KQkq - 0 1");
        Assert.assertEquals(checkmateMove2.isCheck(), true);
        Assert.assertEquals(checkmateMove2.isCheckMate(), true);
        State checkmateMove3 = engine.move("2q5/8/7k/4n3/7K/1r6/8/8 b KQkq - 0 1", 2, 3);
        Assert.assertEquals(checkmateMove3.getBoardRep(), "3q4/8/7k/4n3/7K/1r6/8/8 w KQkq - 0 1");
        Assert.assertEquals(checkmateMove3.isCheck(), true);
        Assert.assertEquals(checkmateMove3.isCheckMate(), true);

        //Check checks
        State checkMove = engine.move("8/6k1/8/4b3/8/K7/8/3q4 b KQkq - 0 1", 28, 19);
        Assert.assertEquals(checkMove.getBoardRep(), "8/6k1/3b4/8/8/K7/8/3q4 w KQkq - 0 1");
        Assert.assertEquals(checkMove.isCheck(), true);
        State checkMove2 = engine.move("8/2k5/6p1/8/8/3Q4/7K/r7 w KQkq - 0 1", 43, 42);
        Assert.assertEquals(checkMove2.getBoardRep(), "8/2k5/6p1/8/8/2Q5/7K/r7 b KQkq - 0 1");
        Assert.assertEquals(checkMove2.isCheck(), true);
        State checkMove3 = engine.move("2n5/k3p3/5Q2/1p6/p6P/P3b3/8/5K2 w KQkq - 0 1", 21, 12);
        Assert.assertEquals(checkMove3.getBoardRep(), "2n5/k3Q3/8/1p6/p6P/P3b3/8/5K2 b KQkq - 0 1");
        Assert.assertEquals(checkMove3.isCheck(), true);
    }

    @Test
    public void moveHintTest(){

    }

}
