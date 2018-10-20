package engine;
import java.util.Random;

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
        
        m.generateMoves(Board.toSquare(pieceLoc), b, MoveGenerator.userMode);
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
        Board b = new Board();
        b.deserializeBoard(stateString);

        return b.completePromotion(stateString, to, promotionPiece);
    }

    @Override
    public State requestMove(String stateString) {
        Board b = new Board();
        b.deserializeBoard(stateString);

        MoveGenerator mg = new MoveGenerator();
        mg.generateMoves(b, MoveGenerator.aiMode);

        Random r = new Random();
        int length = mg.getMoves().size();
        if (length != 0) {
            Move m = mg.getMoves().get(r.nextInt(length));
            return b.psuedoLegalMakeMove(stateString, m);
        } else {
            State s = new State();
            s.setBoardRep(stateString);
            return s;
        }
    }

}
