package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.model.StateContainer;
import app.model.move.AvailableMoveRequest;
import app.model.move.AvailableMoveResponse;
import app.model.move.MoveRequest;
import engine.State;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "move controller",description = "Handle all the move request")
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @RequestMapping(value = "/api/move", method = RequestMethod.POST)
    @ApiOperation(value = "handle single move",response = StateContainer.class)
    public StateContainer handleMove(@RequestBody MoveRequest info) {
        State returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());

        return StateContainer.build(returnValue);
    }

    @ApiOperation(value = "Get all the available move",response = AvailableMoveResponse.class)
    @RequestMapping(value = "/api/move/available", method = RequestMethod.POST)
    public AvailableMoveResponse handleAvailableMove(@RequestBody AvailableMoveRequest am) {
        int[] hint = engine.getMoveHint(am.getState(), am.getFrom());
        AvailableMoveResponse model = new AvailableMoveResponse();
        model.setFromPost(am.getFrom());
        model.setHint(hint);
        return model;
    }
}
