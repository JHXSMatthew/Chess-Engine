package model.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class JoinGameResponse {
    @JsonProperty("playerType")
    private PlayerType playerType;
    @JsonProperty("gameId")
    private UUID gameId;

    public JoinGameResponse() {
    }


    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public enum PlayerType{
        white,black;

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
