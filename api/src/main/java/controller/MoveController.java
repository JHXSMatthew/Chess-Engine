package controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import model.AvailableMoveRequestModel;
import model.AvailableMoveResponseModel;
import model.MoveRequestModel;
import model.StateContainer;
import org.springframework.web.bind.annotation.*;

@RestController
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @RequestMapping(value = "/api/move",method=RequestMethod.POST)
    public StateContainer handleMove(@RequestBody MoveRequestModel info){
        String returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());
        StateContainer container = new StateContainer();
        container.setState(returnValue);
        return container;
    }

    @RequestMapping(value = "/api/move/available", method=RequestMethod.POST)
    public AvailableMoveResponseModel handleAvailableMove(@RequestBody AvailableMoveRequestModel am){
        int[] hint = engine.getMoveHint(am.getState(), am.getFrom());
        return new AvailableMoveResponseModel(am.getFrom(),hint);
    }
}
