package app.model.queue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by JHXSMatthew on 14/10/18.
 */
public class JoinQueueResponse {

    @JsonProperty
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
