package app.model.user;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

// maybe need a generated UUID?
@Entity
@Table(name="user_container")
public class UserContainer implements Serializable {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column
    private String UserName;
    @Column
    private String GameId;
    @Column
    private String PlayerType;
    @Column
    private String Password;


    public UserContainer(){}

    public UserContainer(String gameId, String playerType, String UserName, String Password){
        this.GameId = gameId;
        this.PlayerType = playerType;
        this.UserName = UserName;
        this.Password = Password;
    }

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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
