package app.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponseModel {
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("email")
    private String email;

    @JsonProperty("MMR")
    private int MMR;

    @JsonProperty("matchPlayed")
    private int matchGamePlayed;
    @JsonProperty("matchWin")
    private int matchGameWin;


    @JsonProperty("rankGamePlayed")
    private int rankGamePlayed;
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
