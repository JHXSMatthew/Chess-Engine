package engine;

/**
 * Created by JHXSMatthew on 6/9/18.
 */
public interface ChessEngineI {
    /**
     *
     * @return the board rep string
     */
    String getInitState();
    /**
     *
     * @param state the current state
     * @param pieceLoc range from 0 to 63
     * @return a list of possible move loc
     */
    int[] getMoveHint(String state, int pieceLoc);
    /**
     *
     * @param state the state before move
     * @param from from loc
     * @param to after loc
     * @return the state after move
     */
    State move(String state, int from, int to);

    State promotionMove(String state, int to, int promotionPiece);

    State requestMove(String state);
}