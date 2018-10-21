package app.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class UserResponseModel {
    @ApiModelProperty(notes = "User's user name")
    @JsonProperty("userName")
    private String userName;
    @ApiModelProperty(notes = "User's email")
    @JsonProperty("email")
    private String email;

    @ApiModelProperty(notes = "User's MMR")
    @JsonProperty("MMR")
    private int MMR;

    @ApiModelProperty(notes = "User's number of match game played")
    @JsonProperty("matchPlayed")
    private int matchGamePlayed;
    @ApiModelProperty(notes = "User's number of match game won")
    @JsonProperty("matchWin")
    private int matchGameWin;

    @ApiModelProperty(notes = "User's number of rank game played")
    @JsonProperty("rankGamePlayed")
    private int rankGamePlayed;
    @ApiModelProperty(notes = "User's number of rank game won")
    @JsonProperty("rankGameWin")
    private int rankGameWin;


    public int getMMR() {
        return MMR;
    }

    public void setMMR(int MMR) {
        this.MMR = MMR;
    }

    public int getMatchGamePlayed() {
        return matchGamePlayed;
    }

    public void setMatchGamePlayed(int matchGamePlayed) {
        this.matchGamePlayed = matchGamePlayed;
    }

    public int getMatchGameWin() {
        return matchGameWin;
    }

    public void setMatchGameWin(int matchGameWin) {
        this.matchGameWin = matchGameWin;
    }

    public int getRankGamePlayed() {
        return rankGamePlayed;
    }

    public void setRankGamePlayed(int rankGamePlayed) {
        this.rankGamePlayed = rankGamePlayed;
    }

    public int getRankGameWin() {
        return rankGameWin;
    }

    public void setRankGameWin(int rankGameWin) {
        this.rankGameWin = rankGameWin;
    }

    public UserResponseModel(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
