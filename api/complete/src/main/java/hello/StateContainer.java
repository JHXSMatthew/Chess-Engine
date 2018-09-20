package hello;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateContainer {
    @JsonProperty("currentState")
    private String curentState;

    public StateContainer(){}

    public StateContainer(String state){
        this.curentState = state;
    }

    public String getCurentState() {
        return curentState;
    }

    public void setCurentState(String curentState) {
        this.curentState = curentState;
    }
}
