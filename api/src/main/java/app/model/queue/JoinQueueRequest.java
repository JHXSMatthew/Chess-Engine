package app.model.queue;

import app.model.game.GameRoom;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JHXSMatthew on 14/10/18.
 */
public class JoinQueueRequest {


    @JsonProperty("token")
    private String token;
    @JsonProperty("gameType")
    private GameRoom.GameType gameType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GameRoom.GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameRoom.GameType gameType) {
        this.gameType = gameType;
    }
}
