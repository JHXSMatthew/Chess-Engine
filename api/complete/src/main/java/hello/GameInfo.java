package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameInfo {
    @JsonProperty("GameStatus")
    private String GameStatus;
    @JsonProperty("BoardState")
    private String BoardState;

    public GameInfo(String status, String state){
        this.GameStatus = status;
        this.BoardState = state;
    }

    public GameInfo(){}

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
