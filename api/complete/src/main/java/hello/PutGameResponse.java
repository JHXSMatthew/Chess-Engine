package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PutGameResponse {
    @JsonProperty("playerType")
    private String playerType;

    public PutGameResponse(){}

    public PutGameResponse(String playerType){
        this.playerType = playerType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
}
