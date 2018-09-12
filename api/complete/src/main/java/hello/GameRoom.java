package hello;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "GameRoom")
public class GameRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID roomId;
    @NotBlank
    @Column(nullable = false)
    private String state;
    @NotBlank
    @Column(nullable = false)
    private Integer numOfUser;
    @NotBlank
    @Column(nullable = false)
    private String status;

    public UUID getRoomId() {
        return roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
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
}
