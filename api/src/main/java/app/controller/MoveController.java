package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.model.StateContainer;
import app.model.move.AvailableMoveRequestModel;
import app.model.move.AvailableMoveResponseModel;
import app.model.move.MoveRequestModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @RequestMapping(value = "/api/move", method = RequestMethod.POST)
    public StateContainer handleMove(@RequestBody MoveRequestModel info) {
        String returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());
        StateContainer container = new StateContainer();
        container.setState(returnValue);
        return container;
    }

    @RequestMapping(value = "/api/move/available", method = RequestMethod.POST)
    public AvailableMoveResponseModel handleAvailableMove(@RequestBody AvailableMoveRequestModel am) {
        int[] hint = engine.getMoveHint(am.getState(), am.getFrom());
        return new AvailableMoveResponseModel(am.getFrom(), hint);
    }
}
