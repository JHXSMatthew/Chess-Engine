package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.model.StateContainer;
import app.model.move.AvailableMoveRequest;
import app.model.move.AvailableMoveResponse;
import app.model.move.MoveRequest;
import engine.State;
import org.springframework.web.bind.annotation.*;

@RestController
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @RequestMapping(value = "/api/move", method = RequestMethod.POST)
    public StateContainer handleMove(@RequestBody MoveRequest info) {
        State returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());

        return StateContainer.build(returnValue);
    }

    @RequestMapping(value = "/api/move/available", method = RequestMethod.POST)
    public AvailableMoveResponse handleAvailableMove(@RequestBody AvailableMoveRequest am) {
        int[] hint = engine.getMoveHint(am.getState(), am.getFrom());
        AvailableMoveResponse model = new AvailableMoveResponse();
        model.setFromPost(am.getFrom());
        model.setHint(hint);
        return model;
    }


    @PostMapping(value = "/api/move/checkPromotion")
    public boolean checkPromotion(@RequestBody State state){
        return state.isPromotion();
    }

    
    //State promotionMove(String state, int to, int promotionPiece);
    @PostMapping(value = "/api/move/PromotionMove")
    public StateContainer HandlePromotion(@RequestBody MoveRequest info){
        State returnValue = engine.promotionMove(info.getState(),info.getTo(),info.getPromotion());
        return StateContainer.build(returnValue);
    }
}
