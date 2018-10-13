package app.model;

import app.model.move.MoveHistory;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.State;

public class StateContainer {
    //DON'T REMOVE THE JSON PROPERTY.
    private String state;
    @JsonProperty("isChecked")
    private boolean isChecked;
    @JsonProperty("isCheckmate")
    private boolean isCheckmate;
    @JsonProperty("isResigned")
    private boolean isResigned;

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
