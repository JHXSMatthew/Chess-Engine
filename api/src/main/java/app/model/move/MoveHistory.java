package app.model.move;

import app.model.game.GameRoom;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.Game;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "move_history_final")
public class MoveHistory  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "This history's id")
    private Integer id;

    @OneToOne
    @ApiModelProperty(notes = "This move history belongs to which games")
    private GameRoom game;

    @Column(nullable = false)
    @ApiModelProperty(notes = "This move's start state")
    private String fromState;

    @Column(nullable = false)
    @ApiModelProperty(notes = "This move's end state")
    private String toState;


    @JsonProperty("from")
    @ApiModelProperty(notes = "This move's start position")
    private int locFrom;

    @JsonProperty("to")
    @ApiModelProperty(notes = "This move's end position")
    private int locTo;

    @JsonProperty("promotionPos")
    @ApiModelProperty(notes = "Promotion to flag. usually empty, has value for promotion move")
    private String promotion;


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


    public GameRoom getGame() {
        return game;
    }

    public void setGame(GameRoom game) {
        this.game = game;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public static MoveHistory build(GameRoom room, MoveRequest request){
        MoveHistory history = new MoveHistory();

        BeanUtils.copyProperties(request, history);

        history.setToState(room.getState().getState());
        history.setFromState(request.getState());
        history.setGame(room);

        return history;

    }

    public static MoveHistory build(GameRoom room, NetworkedPromotionMoveRequest request){
        MoveHistory history = new MoveHistory();

        BeanUtils.copyProperties(request, history);

        history.setPromotion(request.getPromotion());
        history.setToState(room.getState().getState());
        history.setFromState(request.getState());
        history.setGame(room);

        return history;

    }

}
