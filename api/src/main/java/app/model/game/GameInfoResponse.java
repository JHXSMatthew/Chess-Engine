package app.model.game;

import app.model.NetworkedStateContainer;
import app.model.StateContainer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GameInfoResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("state")
    private NetworkedStateContainer state;
    @JsonProperty("resignedPlayer")
    private String resignedPlayer;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NetworkedStateContainer getState() {
        return state;
    }

    public void setState(NetworkedStateContainer state) {
        this.state = state;
    }

    public String getResignedPlayer() {
        return resignedPlayer;
    }

    public void setResignedPlayer(String resignedPlayer) {
        this.resignedPlayer = resignedPlayer;
    }

    public GameInfoResponse() {
    }

}


