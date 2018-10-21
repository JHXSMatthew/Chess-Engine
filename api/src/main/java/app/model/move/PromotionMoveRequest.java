package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PromotionMoveRequest {
    @JsonProperty("state")
    private String state ;
    @JsonProperty("to")
    private int to;
    @JsonProperty("promotionPos")
    private String promotion;

    public PromotionMoveRequest() {
    }

    public String getState() {
        return state;
    }

    public int getTo() {
        return to;
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
