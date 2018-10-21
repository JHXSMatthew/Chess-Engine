package app.model.queue;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by JHXSMatthew on 14/10/18.
 */
public class JoinQueueResponse {

    @JsonProperty
    @ApiModelProperty(value = "Current queue id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
