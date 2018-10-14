package app.controller;

import app.model.game.NetworkedMoveRequest;
import app.model.move.MoveHistory;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.exception.*;
import app.model.*;
import app.model.game.GameInfoResponse;
import app.model.game.GameRoom;
import app.model.game.JoinGameResponse;
import app.model.move.MoveRequest;
import engine.State;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.repository.*;

import java.util.*;

@CrossOrigin
@RestController
@Api(value="Game Controller",description="Handle all the game related operations")
public class GameController {

    private static ChessEngineI engine = new ChessEngineDummy();

    @Autowired
    private GameRoomRepository grr;
    @Autowired
    private MoveHistoryRepository mhr;

    @ApiOperation(value = "Make a new Game", response = JoinGameResponse.class)
    @PostMapping("/api/game")
    public JoinGameResponse newGame() {
        //construct a new game instance
        String initState = engine.getInitState();
        GameRoom gr = new GameRoom();
        gr.setNumOfUser(1);

        StateContainer container = new StateContainer();
        container.setChecked(false);
        container.setCheckmate(false);
        container.setState(initState);
        gr.setState(container);

        gr.setStatus(GameRoom.GameStatus.lobby);
        grr.save(gr);

        JoinGameResponse response = new JoinGameResponse();
        response.setPlayerType(JoinGameResponse.PlayerType.b);
        response.setGameId(gr.getId());
        return response;
    }

    @GetMapping("/api/game/{id}")
    @ApiOperation(value = "Get game's status and state information", response = GameInfoResponse.class)
    // Get status and state
    public GameInfoResponse getGameInfo(@PathVariable String id) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            GameInfoResponse info = new GameInfoResponse();
            Optional<MoveHistory> mhs = mhr.findLastHistoryByGameId(id);
            NetworkedStateContainer networkedStateContainer = null;
            if(mhs.isPresent()){
                networkedStateContainer = NetworkedStateContainer.build(dbModel.get().getState(), mhs.get());

            }else{
                networkedStateContainer = new NetworkedStateContainer();
                BeanUtils.copyProperties(dbModel.get().getState(), networkedStateContainer);
            }

            info.setState(networkedStateContainer);

            info.setStatus(dbModel.get().getStatus().toString());
            info.setResignedPlayer(dbModel.get().getResignedPlayer());
            return info;
        }else {
            throw new ResourceNotFoundException();
        }
    }

    @PatchMapping("/api/game/{id}")
    @ApiOperation(value="handle the network game",response = NetworkedStateContainer.class)
    public NetworkedStateContainer handlePatch(@PathVariable String id, @RequestBody NetworkedMoveRequest request) {
        Optional<GameRoom> dbModel = grr.findById(id);

        if(dbModel.isPresent()){
            if(dbModel.get().getStatus() == GameRoom.GameStatus.ingame){
                State s =  engine.move(dbModel.get().getState().getState(),
                        request.getFrom(), request.getTo());
                if(s.isCheckMate()){
                    dbModel.get().setStatus(GameRoom.GameStatus.finished);
                }
                //set field
                dbModel.get().setState(StateContainer.build((s)));

                //save to db by transaction

                try {
                    grr.save(dbModel.get());
                    MoveHistory history = null;
                    if(!request.getState().equals(s.getBoardRep())){
                        //only store legal moves
                        history = MoveHistory.build(dbModel.get(), request);
                        mhr.save(history);
                    }

                    return NetworkedStateContainer.build(StateContainer.build(s), history);

                } catch (HibernateException exObj) {

                    throw new InternalError();
                }
            }else{
                throw new IllegalStateExceptionInternal();
            }

        }else{
            throw new ResourceNotFoundException();
        }
    }

    @PostMapping("/api/game/{id}/resign")
    @ApiOperation(value = "To resign the user's playerType")
    public void handleResignPost(@PathVariable String id, @RequestParam String playerType){
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()) {
            dbModel.get().setResignedPlayer(playerType);
            dbModel.get().setStatus(GameRoom.GameStatus.finished);
            grr.save(dbModel.get());
        }
    }

    @PutMapping("/api/game/{id}")
    @ApiOperation(value="Join the game by id", response = JoinGameResponse.class)
    public JoinGameResponse handlePutAction(@PathVariable String id) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()){
            if(dbModel.get().getNumOfUser() == 1){
                dbModel.get().setNumOfUser(2);
                dbModel.get().setStatus(GameRoom.GameStatus.ingame);

                grr.save(dbModel.get());

                JoinGameResponse response = new JoinGameResponse();
                response.setPlayerType(JoinGameResponse.PlayerType.w);
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
