package engine;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by JHXSMatthew on 17/9/18.
 */
public class PositionTest {

    @Test
    public void de(){
        Board p = new Board();

        p.deserializeBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Assert.assertEquals(Piece.WHITE, p.activeColour);
        Assert.assertEquals(Piece.BLACK_ROOK, p.board[0]);
        Assert.assertEquals(Piece.BLACK_KNIGHT, p.board[1]);
        Assert.assertEquals(Piece.BLACK_BISHOP, p.board[2]);
        Assert.assertEquals(Piece.BLACK_QUEEN,p.board[3]);
        Assert.assertEquals(Piece.BLACK_KING,p.board[4]);

        for(int i =  0; i < 128 ; i ++){

            if(i % 16 == 0 && i != 0){
                System.out.println();
            }
            System.out.print(p.board[i] + " ");
        }


        System.out.println(" ");
        System.out.println(" ");


        for(int i =  0; i < 64 ; i ++){

            if(i % 8 == 0 && i != 0){
                System.out.println();
            }
            System.out.print(i + " ");
        }

    }

    @Test
    public void mv(){
        Board p = new Board();

        p.deserializeBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
        Assert.assertEquals(Piece.BLACK_PAWN,p.board[Board.toSquare(8)]);
        Assert.assertEquals(Piece.NO_PIECE,p.board[Board.toSquare(16)]);
        p.makeMove(new Move(8,16));
        Assert.assertEquals(Piece.BLACK_PAWN,p.board[Board.toSquare(16)]);
        Assert.assertEquals(Piece.NO_PIECE,p.board[Board.toSquare(8)]);
    }
}
