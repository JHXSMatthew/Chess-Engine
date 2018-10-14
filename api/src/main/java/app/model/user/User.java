package app.model.user;

import app.model.queue.QueueEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;

// maybe need a generated UUID?
@Entity
@Table(name="user", indexes = {
        @Index(name="userName" , columnList = "userName", unique = true)})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name= "userName", nullable = false)
    private String userName;

    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    private String email;

    @Column
    private int MMR = 1000;

    @Column
    private int matchGamePlayed = 0;
    @Column
    private int matchGameWin = 0;


    @Column
    private int rankGamePlayed = 0;
    @Column
    private int rankGameWin = 0;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public static MMRComparator comparator = new MMRComparator();

    static class MMRComparator implements Comparator<User>
    {

        @Override
        public int compare(User o1, User o2) {
            return o1.getMMR() - o2.getMMR();
        }
    }

}
