package app.controller;

import app.model.StateContainer;
import app.model.move.MoveRequest;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import engine.State;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class AIGameController {

    private static ChessEngineI engine = new ChessEngineDummy();

    @PostMapping("/api/ai")
    public StateContainer post(@RequestBody MoveRequest request) {
        State result = engine.move(request.getState(), request.getFrom(), request.getTo());
        if(result.getBoardRep().equals(request.getState())){
         return StateContainer.build(result);
        }else{
            State aiResult = engine.requestMove(result.getBoardRep());
            return StateContainer.build(aiResult);
        }
    }




}
