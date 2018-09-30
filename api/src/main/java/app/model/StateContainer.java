package app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import engine.State;

public class StateContainer {
    @JsonProperty("state")
    private String state;
    @JsonProperty("isChecked")
    private boolean isChecked;
    @JsonProperty("isCheckmate")
    private boolean isCheckmate;

    public StateContainer() {
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

    public static StateContainer build(State s){
        if(s == null){
            return null;
        }
        StateContainer container = new StateContainer();
        container.setState(s.getBoardRep());
        container.setChecked(s.isCheck());
        container.setCheckmate(s.isCheckMate());

        return container;
    }
}
