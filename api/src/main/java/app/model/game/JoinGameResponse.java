package app.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class JoinGameResponse {
    @JsonProperty("playerType")
    private PlayerType playerType;
    @JsonProperty("gameId")
    private String gameId;

    public JoinGameResponse() {
    }


    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public enum PlayerType{
        w("w"),b("b");

        private String rep;

        PlayerType(String rep){
            this.rep = rep;
        }

        @Override
        public String toString() {
            return rep;
        }
    }
}
