package app.model.game;

import app.model.move.MoveRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JHXSMatthew on 1/10/18.
 */
public class NetworkedMoveRequest extends MoveRequest {
    @JsonProperty("playerType")
    private String plyaerType;

    @JsonProperty("resign")
    private boolean resign;

    public String getPlyaerType() {
        return plyaerType;
    }

    public void setPlyaerType(String plyaerType) {
        this.plyaerType = plyaerType;
    }

    public boolean isResign() {
        return resign;
    }

    public void setResign(boolean resign) {
        this.resign = resign;
    }
}
