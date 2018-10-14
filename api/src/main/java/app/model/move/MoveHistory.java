package app.model.move;

import app.model.game.GameRoom;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "move_history_new")
public class MoveHistory  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String gameId;

    @Column(nullable = false)
    private String fromState;

    @Column(nullable = false)
    private String toState;


    @JsonProperty("from")
    private int locFrom;

    @JsonProperty("to")
    private int locTo;


    public int getFrom() {
        return locFrom;
    }


    public void setFrom(int from) {
        this.locFrom = from;
    }


    public int getTo() {
        return locTo;
    }


    public void setTo(int to) {
        this.locTo = to;
    }


    public String getFromState() {
        return fromState;
    }

    public String getToState() {
        return toState;
    }


    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public Integer getId() {
        return this.id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }




    public static MoveHistory build(GameRoom room, MoveRequest request){
        MoveHistory history = new MoveHistory();

        BeanUtils.copyProperties(request, history);

        history.setToState(room.getState().getState());
        history.setFromState(request.getState());
        history.setGameId(room.getId());

        return history;

    }

}
