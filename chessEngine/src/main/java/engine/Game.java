package engine;


public class Game {

    public static void main (String[] argv) {/*
        ChessEngineDummy engine = new ChessEngineDummy();

        Board p = new Board();
        p.deserializeBoard("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        Assert.assertEquals(p.activeColour, Piece.WHITE);
        Assert.assertEquals(p.board[0], Piece.BLACK_ROOK);
        Assert.assertEquals(p.board[1], Piece.BLACK_KNIGHT);
        Assert.assertEquals(p.board[2], Piece.BLACK_BISHOP);
        Assert.assertEquals(p.board[3], Piece.BLACK_QUEEN);
        Assert.assertEquals(p.board[4], Piece.BLACK_KING);

        MoveGenerator g = new MoveGenerator();
        Board b = new Board();
        b.deserializeBoard(engine.getInitState());

        g.generateMoves(16, b);
        Assert.assertEquals(g.targetSquareToIndexArray(), new int[]{32,48});
        g.emptyMoves();
        g.generateMoves(1, b);
        Assert.assertEquals(g.targetSquareToIndexArray(), new int[]{34, 32});
        g.emptyMoves();

        String rookMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 0, 16);
        Assert.assertEquals(rookMove, "1nbqkbnr/8/r7/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        String rookMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 0, 56);
        Assert.assertEquals(rookMove1, "1nbqkbnr/8/8/8/8/8/8/rNBQKBNR b KQkq - 0 1");
        String rookMove2 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 0, 5); //blocked by own piece
        Assert.assertEquals(rookMove2, "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");
        String rookMove3 = engine.move("rnbqkbnr/8/8/8/8/8/Q7/RNB1KBNR w KQkq - 0 1", 0, 56); //blocked by opponent piece
        Assert.assertEquals(rookMove3, "rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1");


        String knightMove = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 1, 11);
        Assert.assertEquals(knightMove, "r1bqkbnr/3n4/8/8/8/8/8/RNBQKBNR b KQkq - 0 1");
        String knightMove1 = engine.move("rnbqkbnr/8/8/8/8/8/8/RNBQKBNR w KQkq - 0 1", 57, 24);
        Assert.assertEquals(knightMove1, "rnbqkbnr/8/8/8/8/N7/8/R1BQKBNR b KQkq - 0 1");
        String knightMove2 = engine.move("r1bqkbnr/8/1n6/8/8/8/8/RNBQKBNR w KQkq - 0 1", 17, 0); //blocked by own piece
        Assert.assertEquals(knightMove2, "r1bqkbnr/8/1n6/8/8/8/8/RNBQKBNR w KQkq - 0 1");

*/

    }
}
