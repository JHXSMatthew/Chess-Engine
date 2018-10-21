package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.model.StateContainer;
import app.model.move.AvailableMoveRequest;
import app.model.move.AvailableMoveResponse;
import app.model.move.MoveRequest;
import engine.State;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

        HashMap<String,Integer> pieceMapping = new HashMap<>();
        pieceMapping.put("WHITE_PAWN",0);
        pieceMapping.put("WHITE_KNIGHT",1);
        pieceMapping.put("WHITE_BISHOP",2);
        pieceMapping.put("WHITE_ROOK",3);
        pieceMapping.put("WHITE_KING",5);
        pieceMapping.put("BLACK_PAWN",6);
        pieceMapping.put("BLACK_KNIGHT",7);
        pieceMapping.put("BLACK_BISHOP",8);
        pieceMapping.put("BLACK_ROOK",9);
        pieceMapping.put("BLACK_QUEEN",10);
        pieceMapping.put("BLACK_KING",11);

        int promotionPiece = pieceMapping.get(info.getPromotion());
        State returnValue = engine.promotionMove(info.getState(),info.getTo(),promotionPiece);
        return StateContainer.build(returnValue);
    }
}
