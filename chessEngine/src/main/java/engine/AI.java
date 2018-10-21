package engine;

public class AI {
    public Move bestMove;

    public AI() {
        bestMove = new Move();
    }

    public void minimax(Board b) {
        Board copy = b.copy(b);

        MoveGenerator mg = new MoveGenerator();
        mg.generateMoves(copy, MoveGenerator.aiMode);

        int value = Integer.MIN_VALUE;
        for (Move m: mg.getMoves()) {
            copy.psuedoLegalMakeMove(m);
            if (copy.isValid) {//try other values of depth maybe
                Integer boardValue = minimaxHelper(4, copy, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                copy.restoreBoard(b);

                if (boardValue >= value) {
                    value = boardValue;
                    bestMove = m;
                }
            } else {
              copy.restoreBoard(b);
            }
        }
    }

    //uses alpha beta pruning
    public Integer minimaxHelper(int depth, Board b, Integer alpha, Integer beta, boolean isMaximise) {
        if (depth == 0) {
            return -b.evaluateBoard(b.activeColour);
        }

        Board copy = b.copy(b);
        MoveGenerator mg = new MoveGenerator();
        mg.generateMoves(copy, MoveGenerator.aiMode);

        Integer value = 0;
        if (isMaximise) {
            value = Integer.MIN_VALUE;
            for (Move m: mg.getMoves()) {
                copy.psuedoLegalMakeMove(m);
                if (copy.isValid) {
                    value = Math.max(value, minimaxHelper(depth - 1, copy, alpha, beta, !isMaximise));
                    copy.restoreBoard(b);
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha) {
                        return value;
                    }
                } else {
                    copy.restoreBoard(b);
                }
            }
        } else {
            value = Integer.MAX_VALUE;
            for (Move m: mg.getMoves()) {
                copy.psuedoLegalMakeMove(m);
                if (copy.isValid) {
                    value = Math.min(value, minimaxHelper(depth - 1, copy, alpha, beta, !isMaximise));
                    copy.restoreBoard(b);
                    beta = Math.min(beta, value);
                    if (beta <= alpha) {
                        return value;
                    }
                } else {
                    copy.restoreBoard(b);
                }
            }
        }
        return value;
    }
}
