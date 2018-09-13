package hello;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class allAvailable {
    // Start position
    @JsonProperty("from")
    private int fromPost;
    @JsonProperty("availableMove")
    private ArrayList<Integer> all_pos;

    public allAvailable(int startPos, ArrayList<Integer> pos){
        this.fromPost = startPos;
        this.all_pos = pos;
    }

    public allAvailable(){}

}
