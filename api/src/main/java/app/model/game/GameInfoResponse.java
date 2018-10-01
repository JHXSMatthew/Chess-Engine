package app.model.game;

import app.model.StateContainer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameInfoResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("state")
    private StateContainer state;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StateContainer getState() {
        return state;
    }

    public void setState(StateContainer state) {
        this.state = state;
    }

    public GameInfoResponse() {
    }
}
