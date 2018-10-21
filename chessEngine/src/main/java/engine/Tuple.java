package engine;

public class Tuple<Integer, Move> {
    public final Integer value;
    public final Move m;

    public Tuple(Integer x, Move y) {
        this.value = x;
        this.m = y;
    }
}