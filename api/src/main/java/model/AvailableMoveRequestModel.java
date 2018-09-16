package model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AvailableMoveRequestModel {
    @JsonProperty("State")
    private String State;
    @JsonProperty("from")
    private int from;

    public AvailableMoveRequestModel(String State, int from){
        this.State = State;
        this.from = from;
    }

    public AvailableMoveRequestModel(){}

    public String getState() {
        return State;
    }

    public int getFrom(){
        return from;
    }
}
