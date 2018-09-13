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
        Position p = new Position();
        p.deserializeBoard(state);
        MoveGenerator m = new MoveGenerator();

        m.generateMoves(pieceLoc, p);
        return m.targetSquareToArray();
    }
    @Override
    public String move(String state, int from, int to) {
        /*
        0 ..        black
        ...         ....
        .. 63       white
         */
        Position p = new Position();
        p.deserializeBoard(state);
        Move m = new Move(from, to);
        Boolean success = p.makeMove(m);
        if (success) {
            return p.serializeBoard();
        } else {
            return state;
        }

    }

}
