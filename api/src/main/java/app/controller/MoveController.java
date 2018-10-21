package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.model.StateContainer;
import app.model.move.AvailableMoveRequest;
import app.model.move.AvailableMoveResponse;
import app.model.move.MoveRequest;
import app.model.move.PromotionMove;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import engine.State;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@Api(value = "move controller",description = "Handle all the move request")
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @RequestMapping(value = "/api/move", method = RequestMethod.POST)
    @ApiOperation(value = "handle single move",response = StateContainer.class)
    public StateContainer handleMove(@RequestBody MoveRequest info) {
        State returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());
        boolean flag = returnValue.isPromotion();
        StateContainer result = new StateContainer();
        result.setState(returnValue.getBoardRep());
        result.setChecked(returnValue.isCheck());
        result.setCheckmate(returnValue.isCheckMate());
        result.setPromotion(flag);

        return result;
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


//    @PostMapping(value = "/api/move/checkPromotion")
//    public boolean checkPromotion(@RequestBody State state){
//        return state.isPromotion();
//    }

    private static final HashMap<String,Integer> pieceMapping = new HashMap<String,Integer>();
    static {
        pieceMapping.put("P", 0);
        pieceMapping.put("N", 1);
        pieceMapping.put("B", 2);
        pieceMapping.put("R", 3);
        pieceMapping.put("Q", 4);
        pieceMapping.put("K", 5);

        pieceMapping.put("p", 6);
        pieceMapping.put("n", 7);
        pieceMapping.put("b", 8);
        pieceMapping.put("r", 9);
        pieceMapping.put("q", 10);
        pieceMapping.put("k", 11);
    }

    // State promotionMove(String state, int to, int promotionPiece);
    @PostMapping(value = "/api/move/PromotionMove")
    public StateContainer HandlePromotion(@RequestBody PromotionMove info){
        int promotionPiece = pieceMapping.get(info.getPromotion());
        State returnValue = engine.promotionMove(info.getState(),info.getTo(),promotionPiece);
        return StateContainer.build(returnValue);
    }
}
