package app.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameInfoResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("state")
    private String state;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GameInfoResponse(String status, String state) {
        this.state = state;
        this.status = status;
    }

    public GameInfoResponse() {
    }
}
