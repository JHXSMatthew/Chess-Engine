package controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import exception.IllegalStateExceptionInternal;
import exception.ResourceNotFoundException;
import model.StateContainer;
import model.game.GameInfoResponse;
import model.game.GameRoom;
import model.game.JoinGameResponse;
import model.move.MoveRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.GameRoomRepository;

import java.util.*;

@CrossOrigin
@RestController
public class GameController {

    private static ChessEngineI engine = new ChessEngineDummy();

    @Autowired
    private GameRoomRepository grr;

    @PostMapping("/game")
    public JoinGameResponse newGame() {
        //construct a new game instance
        String initState = engine.getInitState();
        GameRoom gr = new GameRoom();
        gr.setId(UUID.randomUUID());
        gr.setNumOfUser(1);
        gr.setState(initState);
        gr.setStatus(GameRoom.GameStatus.lobby);
        grr.save(gr);

        JoinGameResponse response = new JoinGameResponse();
        response.setPlayerType(JoinGameResponse.PlayerType.black);
        response.setGameId(gr.getId());
        return response;
    }

    @GetMapping("/game/{id}")
    // Get status and state
    public GameInfoResponse getGameInfo(@RequestParam UUID id) {
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

    @PatchMapping("/game/{id}")
    public StateContainer handlePatch(@RequestParam UUID id, @RequestBody MoveRequestModel request) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            StateContainer returnValue = new StateContainer();

            String afterMove =  engine.move(dbModel.get().getState(),
                    request.getFrom(), request.getTo());

            returnValue.setState(afterMove);
            //update db
            dbModel.get().setState(returnValue.getState());
            return returnValue;
        }else{
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping("/game/{id}")
    public JoinGameResponse handlePutAction(@RequestParam UUID id) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            if(dbModel.get().getNumOfUser() == 1){
                dbModel.get().setNumOfUser(2);
                dbModel.get().setStatus(GameRoom.GameStatus.ingame);

                JoinGameResponse response = new JoinGameResponse();
                response.setPlayerType(JoinGameResponse.PlayerType.white);
                return response;

            }else{
                throw new IllegalStateExceptionInternal();
            }

        }else{
            throw new ResourceNotFoundException();
        }

    }
}
