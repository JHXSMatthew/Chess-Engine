package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class MoveRequest {
    @ApiModelProperty(notes = "requested game's state")
    @JsonProperty("state")
    private String state ;
    @ApiModelProperty(notes = "requested from position")
    @JsonProperty("from")
    private int from;
    @ApiModelProperty(notes = "requested to position")
    @JsonProperty("to")
    private int to;


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

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setState(String state) {
        this.state = state;
    }
}
