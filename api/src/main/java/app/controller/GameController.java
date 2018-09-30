package app.controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.exception.*;
import app.model.*;
import app.model.game.GameInfoResponse;
import app.model.game.GameRoom;
import app.model.game.JoinGameResponse;
import app.model.move.MoveRequestModel;
import engine.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.repository.*;

import java.util.*;

@CrossOrigin
@RestController
public class GameController {

    private static ChessEngineI engine = new ChessEngineDummy();

    @Autowired
    private GameRoomRepository grr;

    @PostMapping("/api/game")
    public JoinGameResponse newGame() {
        //construct a new game instance
        String initState = engine.getInitState();
        GameRoom gr = new GameRoom();
        gr.setNumOfUser(1);
        gr.setState(initState);
        gr.setStatus(GameRoom.GameStatus.lobby);
        grr.save(gr);

        JoinGameResponse response = new JoinGameResponse();
        response.setPlayerType(JoinGameResponse.PlayerType.black);
        response.setGameId(gr.getId());
        return response;
    }

    @GetMapping("/api/game/{id}")
    // Get status and state
    public GameInfoResponse getGameInfo(@PathVariable String id) {
        GameInfoResponse info = new GameInfoResponse();
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            info.setState(dbModel.get().getState());
            info.setStatus(dbModel.get().getStatus().toString());
            return info;
        }else {
            throw new ResourceNotFoundException();
        }
    }

    @PatchMapping("/api/game/{id}")
    public StateContainer handlePatch(@PathVariable String id, @RequestBody MoveRequestModel request) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            State s =  engine.move(dbModel.get().getState(),
                    request.getFrom(), request.getTo());

            dbModel.get().setState(s.getBoardRep());
            grr.save(dbModel.get());

            return StateContainer.build(s);
        }else{
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping("/api/game/{id}")
    public JoinGameResponse handlePutAction(@PathVariable String id) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            if(dbModel.get().getNumOfUser() == 1){
                dbModel.get().setNumOfUser(2);
                dbModel.get().setStatus(GameRoom.GameStatus.ingame);

                grr.save(dbModel.get());

                JoinGameResponse response = new JoinGameResponse();
                response.setPlayerType(JoinGameResponse.PlayerType.white);
                response.setGameId(id);
                return response;

            }else{
                throw new IllegalStateExceptionInternal();
            }

        }else{
            throw new ResourceNotFoundException();
        }

    }
}
