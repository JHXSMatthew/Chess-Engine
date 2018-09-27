package model.move;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMoveResponseModel {
    // Start position
    @JsonProperty("from")
    private int fromPost;
    @JsonProperty("AvailableMoveRequestModel")
    private int[] all_pos;

    public AvailableMoveResponseModel(int startPos, int[] pos) {
        this.fromPost = startPos;
        this.all_pos = pos;
    }

    public AvailableMoveResponseModel() {
    }

}
