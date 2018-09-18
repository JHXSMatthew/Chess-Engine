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
        Board p = new Board();
        p.deserializeBoard(state);
        MoveGenerator m = new MoveGenerator();
        
        m.generateMoves(pieceLoc, p);
        return m.targetSquareToIndexArray();
    }
    @Override
    public String move(String state, int from, int to) {
        /*
        0 ..        black
        ...         ....
        .. 63       white
         */
        Board p = new Board();
        p.deserializeBoard(state);
        Move m = new Move(from, to);
        Boolean success = p.makeMove(m);
        if (success) {
            int row = 0;
            for (int index: Square.values) {
                System.out.print(p.board[index] + " ");
                row ++;
                if (row == 8) {
                    System.out.println(" ");
                    row = 0;
                }
            }
            return p.serializeBoard();
        } else {
            return state;
        }

    }

}
