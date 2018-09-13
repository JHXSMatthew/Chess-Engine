package hello;


import com.fasterxml.jackson.annotation.JsonProperty;

public class availableMove {
    @JsonProperty("State")
    private String State;
    @JsonProperty("from")
    private int from;

    public availableMove(String State, int from){
        this.State = State;
        this.from = from;
    }

    public availableMove(){}

    public String getState() {
        return State;
    }

    public int getFrom(){
        return from;
    }
}
