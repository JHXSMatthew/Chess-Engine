package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveRequest {
    @JsonProperty("state")
    private String state;
    @JsonProperty("from")
    private int from;
    @JsonProperty("to")
    private int to;

    public MoveRequest(String state, int start, int end) {
        this.state = state;
        this.from = start;
        this.to = end;
    }

    public MoveRequest() {
    }

    public String getState() {
        return state;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setState(String state) {
        this.state = state;
    }
}
