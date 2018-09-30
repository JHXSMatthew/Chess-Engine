package app.model.move;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMoveRequestModel {
    @JsonProperty("state")
    private String State;
    @JsonProperty("from")
    private int from;

    public AvailableMoveRequestModel(String State, int from) {
        this.State = State;
        this.from = from;
    }

    public AvailableMoveRequestModel() {
    }

    public String getState() {
        return State;
    }

    public int getFrom() {
        return from;
    }
}
