package hello;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "game_room")
public class GameRoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotBlank
    private String UUID;
    @NotBlank
    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private Integer numOfUser;
    @Column(nullable = false)
    private String status;

    public String getRoomId() {
        return UUID;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId(){return id;}

    public void setId(Integer id){this.id = id;}

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoomId(String roomId) {
        this.UUID = roomId;
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
