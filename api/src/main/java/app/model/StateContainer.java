package app.model;

import app.model.move.MoveHistory;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.State;
import io.swagger.annotations.ApiModelProperty;

public class StateContainer {
    //DON'T REMOVE THE JSON PROPERTY.
    @ApiModelProperty(notes = "Current game state")
    private String state;
    @ApiModelProperty(notes = "Does current game is checked")
    @JsonProperty("isChecked")
    private boolean isChecked;
    @ApiModelProperty(notes = "Does current game is check mate")
    @JsonProperty("isCheckmate")
    private boolean isCheckmate;
    @ApiModelProperty(notes = "Does current game is resigned")
    @JsonProperty("isResigned")
    private boolean isResigned;

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    @ApiModelProperty(notes = "Does current state is promotion")
    @JsonProperty("isPromotion")
    private boolean isPromotion;

    public StateContainer() {

    }

    protected StateContainer(State s) {
        if(s == null){
            return;
        }

        setState(s.getBoardRep());
        setChecked(s.isCheck());
        setCheckmate(s.isCheckMate());

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    public void setCheckmate(boolean checkmate) {
        isCheckmate = checkmate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isResigned() {
        return isResigned;
    }

    public void setResigned(boolean resigned) {
        isResigned = resigned;
    }

    public static StateContainer build(State s){
        return new StateContainer(s);
    }





}
