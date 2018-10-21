package app.model.game;

import app.model.StateContainer;
import app.model.move.MoveHistory;
import app.model.user.User;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Table(name = "game_room")
public class GameRoom implements Serializable {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid" ,unique = true)
    @ApiModelProperty(notes = "the id for certain game")
    private String id;

    //the board state Object
    @ApiModelProperty(notes="current game state")
    private String state;
    @ApiModelProperty(notes = "is checked or not")
    private boolean icChecked;
    @ApiModelProperty(notes = "is checkmate or not")
    private boolean isCheckmate;

    private boolean isPromotion;


    @OneToOne
    private User Winner = null;


    @Column(nullable = false)
    private Integer numOfUser;

    @Column(nullable = false)
    private GameStatus status;

    public GameStatus getStatus() {
        return status;
    }

    private String resignedPlayer;

    @Column(nullable = false)
    private GameType gameType = GameType.networkedInvited;

    @Column (nullable = false)
    private Calendar date = Calendar.getInstance();

    //a = black
    //b = white
    @OneToOne
    private User playerA = null;

    @OneToOne
    private User playerB = null;

    public User getPlayerA() {
        return playerA;
    }

    public void setPlayerA(User playerA) {
        this.playerA = playerA;
    }

    public User getPlayerB() {
        return playerB;
    }

    public void setPlayerB(User playerB) {
        this.playerB = playerB;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }


    public StateContainer getState() {
        StateContainer container = new StateContainer();
        container.setChecked(icChecked);
        container.setCheckmate(isCheckmate);
        container.setState(state);
        container.setPromotion(isPromotion);
        return container;
    }

    public void setState(StateContainer state) {
        this.state = state.getState();
        this.isCheckmate = state.isCheckmate();
        this.icChecked = state.isChecked();
        this.isPromotion = state.isPromotion();
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Integer getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(Integer numOfUser) {
        this.numOfUser = numOfUser;
    }

    public String getResignedPlayer() {
        return resignedPlayer;
    }

    public void setResignedPlayer(String resignedPlayer) {
        this.resignedPlayer = resignedPlayer;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public User getWinner() {
        return Winner;
    }

    public void setWinner(User winner) {
        Winner = winner;
    }

    public enum GameStatus {
        lobby, ingame, finished;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public enum GameType{
        networkedInvited, match, rank;

        @Override
        public String toString() { return super.toString(); }
    }


}
