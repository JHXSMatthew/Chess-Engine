package model.game;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "game_room")
public class GameRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        lobby(1),ingame(2),finished(3);

        private int status;
        GameStatus(int i){
            this.status = i;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
