package app.controller;

import app.exception.IllegalStateExceptionInternal;
import app.exception.ResourceNotFoundException;
import app.model.NetworkedStateContainer;
import app.model.StateContainer;
import app.model.game.GameInfoResponse;
import app.model.game.GameRoom;
import app.model.game.JoinGameResponse;
import app.model.game.NetworkedMoveRequest;
import app.model.move.MoveHistory;
import app.model.move.MoveRequest;
import app.repository.GameRoomRepository;
import app.repository.MoveHistoryRepository;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import engine.State;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
