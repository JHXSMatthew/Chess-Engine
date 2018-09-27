package app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StateContainer {
    @JsonProperty("state")
    private String state;

    public StateContainer() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
