package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class AvailableMoveResponse {
    // Start position
    @ApiModelProperty(notes = "this move's start point")
    @JsonProperty("from")
    private int fromPost;
    @ApiModelProperty(notes = "All the available position")
    @JsonProperty("available")
    private int[] hint;


    public AvailableMoveResponse() {
    }

    public int getFromPost() {
        return fromPost;
    }

    public void setFromPost(int fromPost) {
        this.fromPost = fromPost;
    }

    public int[] getHint() {
        return hint;
    }

    public void setHint(int[] hint) {
        this.hint = hint;
    }
}
