package app.model.move;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class NetworkedPromotionMoveRequest extends PromotionMoveRequest{
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
