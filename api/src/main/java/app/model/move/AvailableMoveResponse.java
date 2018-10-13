package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMoveResponse {
    // Start position
    @JsonProperty("from")
    private int fromPost;
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
