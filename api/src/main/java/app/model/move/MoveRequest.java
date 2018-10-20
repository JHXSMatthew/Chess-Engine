package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MoveRequest {
    @JsonProperty("state")
    private String state ;
    @JsonProperty("from")
    private int from;
    @JsonProperty("to")
    private int to;
    @JsonProperty("promotionPos")
    private String promotion;

    public MoveRequest() {
    }

    public String getState() {
        return state;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }
}
