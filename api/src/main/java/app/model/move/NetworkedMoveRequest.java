package app.model.move;

import app.model.move.MoveRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by JHXSMatthew on 1/10/18.
 */
public class NetworkedMoveRequest extends MoveRequest {
    @ApiModelProperty(notes = "Player Type either white or black")
    @JsonProperty("playerType")
    private String plyaerType;


    public String getPlyaerType() {
        return plyaerType;
    }

    public void setPlyaerType(String plyaerType) {
        this.plyaerType = plyaerType;
    }

}
