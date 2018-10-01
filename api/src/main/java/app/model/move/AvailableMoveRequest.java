package app.model.move;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMoveRequest {
    @JsonProperty("state")
    private String State;
    @JsonProperty("from")
    private int from;

    public AvailableMoveRequest(String State, int from) {
        this.State = State;
        this.from = from;
    }

    public AvailableMoveRequest() {
    }

    public String getState() {
        return State;
    }

    public int getFrom() {
        return from;
    }
}
