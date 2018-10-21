package app.model.move;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class AvailableMoveRequest {
    @ApiModelProperty(notes = "current game state")
    @JsonProperty("state")
    private String State;
    @ApiModelProperty(notes = "this move's start point")
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
