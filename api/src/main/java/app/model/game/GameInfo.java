package app.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class GameInfo {
    @ApiModelProperty(notes = "current game status")
    @JsonProperty("GameStatus")
    private String GameStatus;
    @ApiModelProperty(notes = "current board state")
    @JsonProperty("BoardState")
    private String BoardState;


    public GameInfo() {
    }

    public String getGameStatus() {
        return GameStatus;
    }

    public void setGameStatus(String gameStatus) {
        GameStatus = gameStatus;
    }

    public String getBoardState() {
        return BoardState;
    }

    public void setBoardState(String boardState) {
        BoardState = boardState;
    }


}
