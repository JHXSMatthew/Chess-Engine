package app.model.move;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMoveRequest {
    @JsonProperty("state")
    private String State;
    @JsonProperty("from")
    private int from;


    public AvailableMoveRequest() {
    }

    public String getState() {
        return State;
    }

    public int getFrom() {
        return from;
    }

    public void setState(String state) {
        State = state;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
