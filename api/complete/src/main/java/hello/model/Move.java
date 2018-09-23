package hello.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.*;

public class Move {
    @JsonProperty("state")
    private String state;
    @JsonProperty("start")
    private int start;
    @JsonProperty("end")
    private int end;

    public Move(String state, int start, int end){
        this.state = state;
        this.start = start;
        this.end = end;
    }

    public Move(){ }
    public String getState(){
        return state;
    }

    public int getStart(){
        return start;
    }

    public int getEnd(){
        return end;
    }

    public void setState(String state) {
        this.state = state;
    }
}
