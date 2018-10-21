package app.model.queue;

import app.model.game.GameRoom;
import app.model.game.JoinGameResponse;
import app.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Created by JHXSMatthew on 14/10/18.
 */
@Entity
@Table( name = "match_queue")
public class QueueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "current queue id")
    private int id;

    @JsonIgnore
    @ApiModelProperty(value = "current user")
    @OneToOne
    private User user = null;


    @Column( nullable = false)
    @ApiModelProperty(value = "current game type")
    private GameRoom.GameType gameType;

    @OneToOne
    @ApiModelProperty(value = "the game that was assigned")
    private GameRoom assignedGame = null;


    private JoinGameResponse.PlayerType playerType = null;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameRoom.GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameRoom.GameType gameType) {
        this.gameType = gameType;
    }

    public GameRoom getAssignedGame() {
        return assignedGame;
    }

    public void setAssignedGame(GameRoom assignedGame) {
        this.assignedGame = assignedGame;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JoinGameResponse.PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(JoinGameResponse.PlayerType playerType) {
        this.playerType = playerType;
    }


}
