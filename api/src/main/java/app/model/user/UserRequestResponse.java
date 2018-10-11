package app.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestResponse {
    @JsonProperty("gameId")
    private String GameId;
    @JsonProperty("playerType")
    private String PlayerType;
    @JsonProperty("userName")
    private String UserName;
    @JsonProperty("passWord")
    private String PassWord;

    public UserRequestResponse(){}

    public String getGameId() {
        return GameId;
    }

    public void setGameId(String gameId) {
        GameId = gameId;
    }

    public String getPlayerType() {
        return PlayerType;
    }

    public void setPlayerType(String playerType) {
        PlayerType = playerType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
