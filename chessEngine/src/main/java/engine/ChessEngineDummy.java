package engine;

/**
 * Created by JHXSMatthew on 6/9/18.
 */
public class ChessEngineDummy implements ChessEngineI {
    /**
     * Created by JHXSMatthew on 6/9/18.
     */
    @Override
    public String getInitState() {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    }
    @Override
    public int[] getMoveHint(String state, int pieceLoc) {
        Board b = new Board();
        b.deserializeBoard(state);
        MoveGenerator m = new MoveGenerator();
        
        m.generateMoves(pieceLoc, b);
        return m.targetSquareToIndexArray();
    }


    @Override
    public State move(String stateString, int from, int to) {
        /*
        0 ..        black
        ...         ....
        .. 63       white
         */
        Board b = new Board();
        b.deserializeBoard(stateString);
        Move m = new Move(from, to);
        m.setOriginPiece(b);
        m.setTargetPiece(b);

        return b.psuedoLegalMakeMove(stateString, m);
    }

    @Override
    public State promotionMove(String stateString, int to, int promotionPiece) {

    }

    @Override
    public State requestMove(String stateString) {
        
    }

}
