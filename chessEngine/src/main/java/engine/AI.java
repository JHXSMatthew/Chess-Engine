package engine;

public class AI {
    public Integer value;
    public Move bestMove;

    public AI() {
        value = Integer.MIN_VALUE;
        bestMove = new Move();
    }

    public void minimax(Board b) {
        Board copy = b.copy(b);

        MoveGenerator mg = new MoveGenerator();
        mg.generateMoves(copy, MoveGenerator.aiMode);

        int value = Integer.MIN_VALUE;
        for (Move m: mg.getMoves()) {
            copy.applyMove(m);
            Integer boardValue = minimaxHelper(2, copy, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            copy.restoreBoard(b);

            if (boardValue >= this.value) {
                bestMove = m;
            }
        }
    }

    public Integer minimaxHelper(int depth, Board b, Integer alpha, Integer beta, boolean isMaximise) {
        if (depth == 0) {
            return -b.evaluateBoard(b.activeColour);
        }

        Board copy = b.copy(b);
        MoveGenerator mg = new MoveGenerator();
        mg.generateMoves(copy, MoveGenerator.aiMode);

        if (isMaximise) {
            Integer value = Integer.MIN_VALUE;
            for (Move m: mg.getMoves()) {
                copy.applyMove(m);
                if (copy.isValid) {
                    value = Math.max(value, minimaxHelper(depth - 1, copy, alpha, beta, !isMaximise));
                }
                copy.restoreBoard(b);
                alpha = Math.max(alpha, value);
                if (beta <= alpha) {
                    return value;
                }
            }
        } else {
            Integer value = Integer.MAX_VALUE;
            for (Move m: mg.getMoves()) {
                copy.applyMove(m);
                if (copy.isValid) {
                    value = Math.min(value, minimaxHelper(depth - 1, copy, alpha, beta, !isMaximise));
                }
                copy.restoreBoard(b);
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    return value;
                }
            }
        }
        return value;
    }
}
