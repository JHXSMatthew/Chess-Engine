package app.model.game;

import app.model.NetworkedStateContainer;
import app.model.StateContainer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.swagger.annotations.ApiModelProperty;

import java.io.IOException;

public class GameInfoResponse {
    @ApiModelProperty(notes = "current game status")
    @JsonProperty("status")
    private String status;
    @ApiModelProperty(notes = "current game state")
    @JsonProperty("state")
    private NetworkedStateContainer state;
    @ApiModelProperty(notes = "user's playType")
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


