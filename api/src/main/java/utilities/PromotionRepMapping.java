package utilities;

import java.util.HashMap;

/**
 * Created by JHXSMatthew on 21/10/18.
 */
public class PromotionRepMapping {

    public static final HashMap<String,Integer> pieceMapping = new HashMap<String,Integer>();
    static {
        pieceMapping.put("P", 0);
        pieceMapping.put("N", 1);
        pieceMapping.put("B", 2);
        pieceMapping.put("R", 3);
        pieceMapping.put("Q", 4);
        pieceMapping.put("K", 5);

        pieceMapping.put("p", 0);
        pieceMapping.put("n", 1);
        pieceMapping.put("b", 2);
        pieceMapping.put("r", 3);
        pieceMapping.put("q", 4);
        pieceMapping.put("k", 5);
    }
}

