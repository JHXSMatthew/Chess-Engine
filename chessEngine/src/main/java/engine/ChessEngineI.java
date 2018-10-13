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

    /**
     * Function is only called after a move returns a state with the isPromotion field set to true.
     * The move would have been verified (the pawn moving up) from the previous move function call.
     * Front end then has to get the piece the player would like to promote it to.
     * Then call this function, which will then complete the promotion, and then recalculate check and checkmate
     *
     * @param state should be the same state returned from the previous move call
     * @param to (the 64-index of the pawn being promoted, it should be the 'to' index in the previous move call))
     * @param promotionPiece is a Piece Type (check in Piece.java, it has no associated colour))
     * @return
     */
    State promotionMove(String state, int to, int promotionPiece);

    /**
     * Very simple requestMove function for the AI, which will simply take in the String state,
     * generate all possible moves for that colour, and use a random() to pick any move
     * and execute it. If the function call fails and returns a state exactly the same as the input state, then just
     * call the function again and hope it chooses another valid move.
     * @param state
     * @return
     */
    State requestMove(String state);
}