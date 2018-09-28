package app.model.game;

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

    @NotBlank
    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private Integer numOfUser;

    @Column(nullable = false)
    private GameStatus status;

    public GameStatus getStatus() {
        return status;
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


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNumOfUser() {
        return numOfUser;
    }

    public void setNumOfUser(Integer numOfUser) {
        this.numOfUser = numOfUser;
    }


    public enum GameStatus{
        lobby,ingame,finished;



        @Override
        public String toString() {
            return super.toString();
        }
    }

}
