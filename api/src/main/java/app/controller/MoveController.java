package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.model.StateContainer;
import app.model.move.AvailableMoveRequestModel;
import app.model.move.AvailableMoveResponseModel;
import app.model.move.MoveRequestModel;
import engine.State;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @RequestMapping(value = "/api/move", method = RequestMethod.POST)
    public StateContainer handleMove(@RequestBody MoveRequestModel info) {
        State returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());

        return StateContainer.build(returnValue);
    }

    @RequestMapping(value = "/api/move/available", method = RequestMethod.POST)
    public AvailableMoveResponseModel handleAvailableMove(@RequestBody AvailableMoveRequestModel am) {
        int[] hint = engine.getMoveHint(am.getState(), am.getFrom());
        AvailableMoveResponseModel model = new AvailableMoveResponseModel();
        model.setFromPost(am.getFrom());
        model.setHint(hint);
        return model;
    }
}
