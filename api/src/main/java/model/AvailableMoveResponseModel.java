package model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class AvailableMoveResponseModel {
    // Start position
    @JsonProperty("from")
    private int fromPost;
    @JsonProperty("AvailableMoveRequestModel")
    private int[] all_pos;

    public AvailableMoveResponseModel(int startPos, int[] pos){
        this.fromPost = startPos;
        this.all_pos = pos;
    }

    public AvailableMoveResponseModel(){}

}
