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
        return new int[0];
    }
    @Override
    public String move(String state, int from, int to) {
        return null;
    }

}