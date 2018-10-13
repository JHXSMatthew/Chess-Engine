package app.model.game;

import app.model.StateContainer;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "game_room")
public class GameRoom implements Serializable {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "uuid", columnDefinition="uuid" ,unique = true)
    private String id;

    private String state;
    private boolean icChecked;
    private boolean isCheckmate;

    @Column(nullable = false)
    private Integer numOfUser;

    @Column(nullable = false)
    private GameStatus status;

    public GameStatus getStatus() {
        return status;
    }

    public String resignedPlayer;

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
        return container;
    }

    public void setState(StateContainer state) {
        this.state = state.getState();
        this.isCheckmate = state.isCheckmate();
        this.icChecked = state.isChecked();
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

    public enum GameStatus{
        lobby,ingame,finished;



        @Override
        public String toString() {
            return super.toString();
        }
    }

}
