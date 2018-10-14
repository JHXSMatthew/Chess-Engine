package utilities;

/**
 * Created by JHXSMatthew on 14/10/18.
 */
public class PairU<K, V> {


    private K fst;
    private V snd;

    public PairU(K fst, V snd){
        this.fst = fst;
        this.snd = snd;
    }

    public K fst(){
        return fst;
    }

    public V snd(){
        return snd;
    }

}
