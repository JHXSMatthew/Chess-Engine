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
        Assert.assertEquals(rookMove.getBoardRep(), "1nbqkbnr/8/r7/8/8/8/8/RNBQKBNR w KQk - 0 1");
        State rookMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0, 56);
        Assert.assertEquals(rookMove1.getBoardRep(), "1nbqkbnr/8/8/8/8/8/8/rNBQKBNR w KQk - 0 1");
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
        Assert.assertEquals(queenMove4.getBoardRep(), "rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR b KQ - 0 1");
        State queenMove5 = engine.move("rnbqkbnr/8/8/8/3N4/8/8/R1BQKBNR w KQkq - 0 1", 3, 59); //blocked by opponent piece
        Assert.assertEquals(queenMove5.getBoardRep(), "rnbqkbnr/8/8/8/3N4/8/8/R1BQKBNR w KQkq - 0 1");

        //King tests
        State kingMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 12);
        Assert.assertEquals(kingMove.getBoardRep(), "rnbq1bnr/4k3/8/8/8/8/8/RNBQKBNR w KQ - 0 1");
        State kingMove2 = engine.move("rnbq1bnr/5k2/8/8/8/8/8/RNBQKBNR b KQ - 0 1", 13, 21);
        Assert.assertEquals(kingMove2.getBoardRep(), "rnbq1bnr/8/5k2/8/8/8/8/RNBQKBNR w KQ - 0 1");
        State kingMove3 = engine.move("rnbq1bnr/8/4k3/8/8/8/8/RNBQKBNR b KQ - 0 1", 20, 28);
        Assert.assertEquals(kingMove3.getBoardRep(), "rnbq1bnr/8/8/4k3/8/8/8/RNBQKBNR w KQ - 0 1");
        State kingMove4 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 3); //blocked by own piece
        Assert.assertEquals(kingMove4.getBoardRep(), "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        State kingMove5 = engine.move("rnbq1bnr/4k3/8/8/8/8/8/R1BQKBNR w KQ - 0 1", 12, 5); //blocked by own piece
        Assert.assertEquals(kingMove5.getBoardRep(), "rnbq1bnr/4k3/8/8/8/8/8/R1BQKBNR w KQ - 0 1");

        //Pawn tests
        State pawnMove = engine.move("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 53, 37);
        Assert.assertEquals(pawnMove.getBoardRep(), "rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq 85 0 1");
        State pawnMove2 = engine.move("rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq - 0 1", 13, 29);
        Assert.assertEquals(pawnMove2.getBoardRep(), "rnbqkbnr/ppppp1pp/8/5p2/5P2/8/PPPPP1PP/RNBQKBNR w KQkq 37 0 1");
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
        State checkmateMove = engine.move("k7/8/6r1/8/8/4bq2/7K/8 b - - 0 1", 44, 37);
        Assert.assertEquals(checkmateMove.getBoardRep(), "k7/8/6r1/8/5b2/5q2/7K/8 w  - 0 1");
        Assert.assertEquals(checkmateMove.isCheck(), true);
        Assert.assertEquals(checkmateMove.isCheckMate(), true);
        State checkmateMove2 = engine.move("8/8/k7/8/8/1R6/7R/5K2 w - - 0 1", 55, 48);
        Assert.assertEquals(checkmateMove2.getBoardRep(), "8/8/k7/8/8/1R6/R7/5K2 b  - 0 1");
        Assert.assertEquals(checkmateMove2.isCheck(), true);
        Assert.assertEquals(checkmateMove2.isCheckMate(), true);
        State checkmateMove3 = engine.move("2q5/8/7k/4n3/7K/1r6/8/8 b - - 0 1", 2, 3);
        Assert.assertEquals(checkmateMove3.getBoardRep(), "3q4/8/7k/4n3/7K/1r6/8/8 w  - 0 1");
        Assert.assertEquals(checkmateMove3.isCheck(), true);
        Assert.assertEquals(checkmateMove3.isCheckMate(), true);

        State checkmateMove4 = engine.move("4k3/4P3/3PK3/8/8/8/8/8 w - - 0 1", 19, 11);
        Assert.assertEquals(checkmateMove4.getBoardRep(), "4k3/3PP3/4K3/8/8/8/8/8 b  - 0 1");
        Assert.assertEquals(checkmateMove4.isCheck(), true);
        Assert.assertEquals(checkmateMove4.isCheckMate(), true);
        State checkmateMove5 = engine.move("6k1/5ppp/6r1/8/8/7P/R4PP1/6K1 w - - 0 1", 48, 0);
        Assert.assertEquals(checkmateMove5.getBoardRep(), "R5k1/5ppp/6r1/8/8/7P/5PP1/6K1 b  - 0 1");
        Assert.assertEquals(checkmateMove5.isCheck(), true);
        Assert.assertEquals(checkmateMove5.isCheckMate(), true);
        State checkmateMove6 = engine.move("r4rk1/ppp2ppp/8/8/8/1P6/PQ3PPP/B4RK1 w - - 0 1", 49, 14);
        Assert.assertEquals(checkmateMove6.getBoardRep(), "r4rk1/ppp2pQp/8/8/8/1P6/P4PPP/B4RK1 b  - 0 1");
        Assert.assertEquals(checkmateMove6.isCheck(), true);
        Assert.assertEquals(checkmateMove6.isCheckMate(), true);
        State checkmateMove7 = engine.move("r5rk/ppp3pp/8/4N3/8/1P6/P4PPP/5RK1 w - - 0 1", 28, 13);
        Assert.assertEquals(checkmateMove7.getBoardRep(), "r5rk/ppp2Npp/8/8/8/1P6/P4PPP/5RK1 b  - 0 1");
        Assert.assertEquals(checkmateMove7.isCheck(), true);
        Assert.assertEquals(checkmateMove7.isCheckMate(), true);
        State checkmateMove8 = engine.move("r4rk1/ppp2p1p/5Bp1/8/6N1/1P6/P4PPP/5RK1 w - - 0 1", 38, 23);
        Assert.assertEquals(checkmateMove8.getBoardRep(), "r4rk1/ppp2p1p/5BpN/8/8/1P6/P4PPP/5RK1 b  - 0 1");
        Assert.assertEquals(checkmateMove8.isCheck(), true);
        Assert.assertEquals(checkmateMove8.isCheckMate(), true);
        State checkmateMove9 = engine.move("r4r2/ppp1Nppk/8/3R4/8/1P6/P4PPP/6K1 w - - 0 1", 27, 31);
        Assert.assertEquals(checkmateMove9.getBoardRep(), "r4r2/ppp1Nppk/8/7R/8/1P6/P4PPP/6K1 b  - 0 1");
        Assert.assertEquals(checkmateMove9.isCheck(), true);
        Assert.assertEquals(checkmateMove9.isCheckMate(), true);

        State checkmateMove10 = engine.move("7k/p6p/1p6/8/8/1B6/5K2/2B5 w - - 0 1", 58, 49);
        Assert.assertEquals(checkmateMove10.getBoardRep(), "7k/p6p/1p6/8/8/1B6/1B3K2/8 b  - 0 1");
        Assert.assertEquals(checkmateMove10.isCheck(), true);
        Assert.assertEquals(checkmateMove10.isCheckMate(), true);
        State checkmateMove11 = engine.move("r4rk1/p4ppp/1p5B/8/8/6Q1/P4PPP/5RK1 w - - 0 1", 46, 14);
        Assert.assertEquals(checkmateMove11.getBoardRep(), "r4rk1/p4pQp/1p5B/8/8/8/P4PPP/5RK1 b  - 0 1");
        Assert.assertEquals(checkmateMove11.isCheck(), true);
        Assert.assertEquals(checkmateMove11.isCheckMate(), true);
        State checkmateMove12 = engine.move("r4rk1/p4pp1/1p6/2Q5/7Q/6P1/P4PK1/R6R w - - 0 1", 39, 7);
        Assert.assertEquals(checkmateMove12.getBoardRep(), "r4rkQ/p4pp1/1p6/2Q5/8/6P1/P4PK1/R6R b  - 0 1");
        Assert.assertEquals(checkmateMove12.isCheck(), true);
        Assert.assertEquals(checkmateMove12.isCheckMate(), true);
        State checkmateMove13 = engine.move("7k/Q7/6K1/8/1p6/p7/8/8 w - - 0 1", 8, 0);
        Assert.assertEquals(checkmateMove13.getBoardRep(), "Q6k/8/6K1/8/1p6/p7/8/8 b  - 0 1");
        Assert.assertEquals(checkmateMove13.isCheck(), true);
        Assert.assertEquals(checkmateMove13.isCheckMate(), true);
        State checkmateMove14 = engine.move("1r5k/1p5p/p7/8/8/B7/8/4K1R1 w - - 0 1", 40, 49);
        Assert.assertEquals(checkmateMove14.getBoardRep(), "1r5k/1p5p/p7/8/8/8/1B6/4K1R1 b  - 0 1");
        Assert.assertEquals(checkmateMove14.isCheck(), true);
        Assert.assertEquals(checkmateMove14.isCheckMate(), true);

        //Check checks
        State checkMove = engine.move("8/6k1/8/4b3/8/K7/8/3q4 b - - 0 1", 28, 19);
        Assert.assertEquals(checkMove.getBoardRep(), "8/6k1/3b4/8/8/K7/8/3q4 w  - 0 1");
        Assert.assertEquals(checkMove.isCheck(), true);
        Assert.assertEquals(checkMove.isCheckMate(), false);
        State checkMove2 = engine.move("8/2k5/6p1/8/8/3Q4/7K/r7 w - - 0 1", 43, 42);
        Assert.assertEquals(checkMove2.getBoardRep(), "8/2k5/6p1/8/8/2Q5/7K/r7 b  - 0 1");
        Assert.assertEquals(checkMove2.isCheck(), true);
        Assert.assertEquals(checkMove2.isCheckMate(), false);
        State checkMove3 = engine.move("2n5/k3p3/5Q2/1p6/p6P/P3b3/8/5K2 w - - 0 1", 21, 12);
        Assert.assertEquals(checkMove3.getBoardRep(), "2n5/k3Q3/8/1p6/p6P/P3b3/8/5K2 b  - 0 1");
        Assert.assertEquals(checkMove3.isCheck(), true);
        Assert.assertEquals(checkMove3.isCheckMate(), false);

        //Check illegal moves (moves that put yourself in check)
        State illegalMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 4, 11);
        Assert.assertEquals(illegalMove.getBoardRep(), "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        State illegalMove2 = engine.move("8/2p3k1/1p6/8/8/1B6/7P/7K b - - 0 1", 14, 6);
        Assert.assertEquals(illegalMove2.getBoardRep(), "8/2p3k1/1p6/8/8/1B6/7P/7K b  - 0 1");
        State illegalMove3 = engine.move("8/8/8/3k4/8/4K3/8/8 w - - 0 1", 27, 44);
        Assert.assertEquals(illegalMove3.getBoardRep(), "8/8/8/3k4/8/4K3/8/8 w  - 0 1");
        State illegalMove4 = engine.move("8/R3qk2/7p/5N2/8/2P5/4N3/2K5 b - - 0 1", 12, 52);
        Assert.assertEquals(illegalMove4.getBoardRep(), "8/R3qk2/7p/5N2/8/2P5/4N3/2K5 b  - 0 1");


        //Check en passant moves
        State enPassantMove = engine.move("k7/5p2/8/4P3/8/8/8/K7 b - 0 0 1", 13, 29);
        Assert.assertEquals(enPassantMove.getBoardRep(), "k7/8/8/4Pp2/8/8/8/K7 w  37 0 1");
        State enPassantMove2 = engine.move("k7/8/8/4Pp2/8/8/8/K7 w - 37 0 1", 28, 21);
        Assert.assertEquals(enPassantMove2.getBoardRep(), "k7/8/5P2/8/8/8/8/K7 b  - 0 1");

        State enPassantMove3 = engine.move("k7/8/8/8/4p3/8/5P2/K7 w - 0 0 1", 53, 37);
        Assert.assertEquals(enPassantMove3.getBoardRep(), "k7/8/8/8/4pP2/8/8/K7 b  85 0 1");
        State enPassantMove4 = engine.move("k7/8/8/8/4pP2/8/8/K7 b - 85 0 1", 36, 45);
        Assert.assertEquals(enPassantMove4.getBoardRep(), "k7/8/8/8/8/5p2/8/K7 w  - 0 1");


        //Check castling moves
        State castlingMove1 = engine.move("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1", 4, 2);
        Assert.assertEquals(castlingMove1.getBoardRep(), "2kr3r/8/8/8/8/8/8/R3K2R w KQ - 0 1");
        State castlingMove2 = engine.move("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1", 4, 6);
        Assert.assertEquals(castlingMove2.getBoardRep(), "r4rk1/8/8/8/8/8/8/R3K2R w KQ - 0 1");
        State castlingMove3 = engine.move("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1", 60, 62);
        Assert.assertEquals(castlingMove3.getBoardRep(), "r3k2r/8/8/8/8/8/8/R4RK1 b kq - 0 1");
        State castlingMove4 = engine.move("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1", 60, 58);
        Assert.assertEquals(castlingMove4.getBoardRep(), "r3k2r/8/8/8/8/8/8/2KR3R b kq - 0 1");
    }

    @Test
    public void moveHintTest(){
        ChessEngineDummy engine = new ChessEngineDummy();

        int[] moveHint1 = engine.getMoveHint("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 0);
        for(int i = 0; i < moveHint1.length; i++)
        {
            //System.out.println(moveHint1[i]);
            Assert.assertEquals(moveHint1[i] % 8, 0);
        }

        int[] moveHint2 = engine.getMoveHint("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR b KQkq - 0 1", 6);
        int[] moveHint2Result = new int[]{23, 21, 12};
        for(int i = 0; i < moveHint2.length; i++)
        {
            //System.out.println(moveHint2[i]);
        }
        Assert.assertArrayEquals(moveHint2, moveHint2Result);

        int[] moveHint3 = engine.getMoveHint("rnbqkbnr/8/8/8/4B3/8/8/RNBQK1NR w KQkq - 0 1", 36);
        int[] moveHint3Result = new int[]{45, 54, 43, 50, 29, 22, 15, 27, 18, 9, 0};
        for(int i = 0; i < moveHint3.length; i++)
        {
            //System.out.println(moveHint3[i]);
        }
        Assert.assertArrayEquals(moveHint3, moveHint3Result);

        int[] moveHint4 = engine.getMoveHint("6k1/r7/3P2b1/1N6/8/3Q4/4K3/8 w KQkq - 0 1", 43);
        int[] moveHint4Result = new int[]{51, 59, 44, 45, 46, 47, 35, 27, 42, 41, 40, 50, 57, 36, 29, 22, 34};
        for(int i = 0; i < moveHint4.length; i++)
        {
            //System.out.println(moveHint4[i]);
        }
        Assert.assertArrayEquals(moveHint4, moveHint4Result);

        int[] moveHint5 = engine.getMoveHint("2k5/8/8/6N1/8/8/8/1K6 w KQkq - 0 1", 30);
        int[] moveHint5Result = new int[]{47, 45, 36, 15, 13, 20};
        for(int i = 0; i < moveHint5.length; i++)
        {
            //System.out.println(moveHint5[i]);
        }
        Assert.assertArrayEquals(moveHint5, moveHint5Result);
    }

}