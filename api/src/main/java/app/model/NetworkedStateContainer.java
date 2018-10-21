package app.model;

import app.model.move.MoveHistory;
import com.fasterxml.jackson.annotation.JsonProperty;
import engine.State;
import org.springframework.beans.BeanUtils;

/**
 * Created by JHXSMatthew on 9/10/18.
 */
public class NetworkedStateContainer extends StateContainer {


    @JsonProperty("lastMove")
    private MoveHistory history;




    public static NetworkedStateContainer build(StateContainer s, MoveHistory history){
        if(s == null){
            return null;
        }

        NetworkedStateContainer container = new NetworkedStateContainer();

        BeanUtils.copyProperties(s, container);
        container.history = history;

        return container;


    }

}
