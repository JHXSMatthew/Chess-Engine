package model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class AvailableMoveResponseModel {
    // Start position
    @JsonProperty("from")
    private int fromPost;
    @JsonProperty("AvailableMoveRequestModel")
    private ArrayList<Integer> all_pos;

    public AvailableMoveResponseModel(int startPos, ArrayList<Integer> pos){
        this.fromPost = startPos;
        this.all_pos = pos;
    }

    public AvailableMoveResponseModel(){}

}
