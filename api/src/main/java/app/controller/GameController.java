package app.controller;

import app.model.move.NetworkedPromotionMoveRequest;
import app.model.move.NetworkedMoveRequest;
import app.model.move.MoveHistory;
import app.model.user.Token;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import app.exception.*;
import app.model.*;
import app.model.game.GameInfoResponse;
import app.model.game.GameRoom;
import app.model.game.JoinGameResponse;
import engine.State;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.repository.*;
import utilities.PromotionRepMapping;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@Api(value="Game Controller",description="Handle all the game related operations")
public class GameController {

    private static ChessEngineI engine = new ChessEngineDummy();

    @Autowired
    private GameRoomRepository grr;
    @Autowired
    private UserRepository ur;

    @Autowired
    private MoveHistoryRepository mhr;

    @Autowired
    private TokenRepository tokenRepository;

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

    @GetMapping("/api/game")
    public ResponseEntity<List<GameRoom>> gameHistory(@RequestParam String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Token> tObj = tokenRepository.findByToken(token);
        if (tObj.isPresent()) {
            tObj.get().getUser();
            Optional<List<GameRoom>> history = grr.findOrderByPlayerAOrPlayerBOrderByIdDesc(tObj.get().getUser(), tObj.get().getUser());
            if (history.isPresent()) {
                List<GameRoom> result = history.get().stream().filter((v) -> {
                    return v.getStatus() == GameRoom.GameStatus.finished;
                }).collect(Collectors.toList());

                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.ok(new ArrayList<GameRoom>());
            }
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/api/game/{id}")
    @ApiOperation(value = "Get game's status and state information", response = GameInfoResponse.class)
    // Get status and state
    public GameInfoResponse getGameInfo(@PathVariable String id) {
        Optional<GameRoom> dbModel = grr.findById(id);
        if (dbModel.isPresent()) {
            GameInfoResponse info = new GameInfoResponse();
            Optional<MoveHistory> mhs = mhr.findFirstByGameOrderByIdDesc(dbModel.get());
            NetworkedStateContainer networkedStateContainer = null;

            if (mhs.isPresent()) {
                networkedStateContainer = NetworkedStateContainer.build(dbModel.get().getState(), mhs.get());

            } else {
                networkedStateContainer = new NetworkedStateContainer();
                BeanUtils.copyProperties(dbModel.get().getState(), networkedStateContainer);
            }

            info.setState(networkedStateContainer);

            info.setStatus(dbModel.get().getStatus().toString());
            info.setResignedPlayer(dbModel.get().getResignedPlayer());
            return info;
        } else {
            throw new ResourceNotFoundException();
        }
    }


    @CrossOrigin(origins = "*")
    @GetMapping("/api/game/{id}/moveHistory")
    public ResponseEntity<List<MoveHistory>> getAllMoveHistory(@PathVariable String id) {
        Optional<GameRoom> gameRoom = grr.findById(id);
        if (gameRoom.isPresent()) {
            Optional<List<MoveHistory>> history = mhr.findByGame(gameRoom.get());
            List<MoveHistory> mhst = history.get();
            return history.isPresent() ? (ResponseEntity.ok(mhst)) : ResponseEntity.ok(new ArrayList<MoveHistory>());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PatchMapping("/api/game/{id}")
    @ApiOperation(value = "handle the network game", response = NetworkedStateContainer.class)
    public NetworkedStateContainer handlePatch(@PathVariable String id, @RequestBody NetworkedMoveRequest request) {
        Optional<GameRoom> dbModel = grr.findById(id);

        if (dbModel.isPresent()) {
            if (dbModel.get().getStatus() == GameRoom.GameStatus.ingame) {
                State s = engine.move(dbModel.get().getState().getState(),
                        request.getFrom(), request.getTo());
                if (s.isCheckMate()) {
                    dbModel.get().setStatus(GameRoom.GameStatus.finished);
                    handleResult(dbModel.get(), request.getPlyaerType().equals("b"));

                }
                //set field
                dbModel.get().setState(StateContainer.build((s)));

                try {
                    grr.save(dbModel.get());
                    MoveHistory history = null;
                    if (!request.getState().equals(s.getBoardRep())) {
                        //only store legal moves
                        history = MoveHistory.build(dbModel.get(), request);
                        mhr.save(history);
                    }

                    return NetworkedStateContainer.build(StateContainer.build(s), history);

                } catch (HibernateException exObj) {

                    throw new InternalError();
                }
            } else {
                throw new IllegalStateExceptionInternal();
            }

        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PatchMapping("/api/game/{id}/promotionMove")
    @ApiOperation(value = "handle the network game", response = NetworkedStateContainer.class)
    public ResponseEntity<NetworkedStateContainer> handlePromotionPatch(@PathVariable String id,
                                                                        @RequestBody NetworkedPromotionMoveRequest request) {
        Optional<GameRoom> dbModel = grr.findById(id);

        if (!dbModel.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        if (dbModel.get().getState().isPromotion()) {
            if (dbModel.get().getStatus() == GameRoom.GameStatus.ingame) {
                int promotionPiece = PromotionRepMapping.pieceMapping.get(request.getPromotion());
                State s = engine.promotionMove(request.getState(), request.getTo(), promotionPiece);

                if (s.isCheckMate()) {
                    dbModel.get().setStatus(GameRoom.GameStatus.finished);
                    handleResult(dbModel.get(), request.getPlyaerType().equals("b"));
                }

                dbModel.get().setState(StateContainer.build((s)));

                try {
                    grr.save(dbModel.get());
                    MoveHistory history = null;
                    if (!request.getState().equals(s.getBoardRep())) {
                        //only store legal moves
                        history = MoveHistory.build(dbModel.get(), request);
//                        mhr.save(history);
                    }

                    return ResponseEntity.ok(NetworkedStateContainer.build(StateContainer.build(s), history));

                } catch (HibernateException exObj) {

                    throw new InternalError();
                }

            } else {
                return ResponseEntity.badRequest().build();
            }


        } else {
            return ResponseEntity.badRequest().build();
        }
    }







    @PostMapping("/api/game/{id}/resign")
    @ApiOperation(value = "To resign the user's playerType")
    public void handleResignPost(@PathVariable String id, @RequestParam String playerType){
        Optional<GameRoom> dbModel = grr.findById(id);
        if(dbModel.isPresent()) {
            dbModel.get().setResignedPlayer(playerType);
            dbModel.get().setStatus(GameRoom.GameStatus.finished);

            handleResult(dbModel.get(), playerType.equals("w"));
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




    private void handleResult(GameRoom room, boolean isBlackWin){


        if(room.getStatus() != GameRoom.GameStatus.finished){
            return;
        }

        room.setWinner(isBlackWin ? room.getPlayerA() : room.getPlayerB());

        if(room.getGameType() == GameRoom.GameType.rank){
            room.getPlayerA().setRankGamePlayed(room.getPlayerA().getRankGamePlayed() + 1);
            room.getPlayerB().setRankGamePlayed(room.getPlayerB().getRankGamePlayed() + 1);

            if(isBlackWin){
                room.getPlayerA().setRankGameWin(room.getPlayerA().getRankGameWin() + 1);
                room.getPlayerA().setMMR(room.getPlayerA().getMMR() + 50);

            }else{
                room.getPlayerB().setRankGameWin(room.getPlayerB().getRankGameWin() + 1);
                room.getPlayerB().setMMR(room.getPlayerA().getMMR() + 50);


            }
        }else if(room.getGameType() == GameRoom.GameType.match){
            room.getPlayerA().setMatchGamePlayed(room.getPlayerA().getMatchGamePlayed() + 1);
            room.getPlayerB().setMatchGamePlayed(room.getPlayerB().getMatchGamePlayed() + 1);

            if(isBlackWin){
                room.getPlayerA().setMatchGameWin(room.getPlayerA().getMatchGameWin() + 1);
            }else{
                room.getPlayerB().setMatchGameWin(room.getPlayerB().getMatchGameWin() + 1);


            }
        }
        ur.save(room.getPlayerA());
        ur.save(room.getPlayerB());
        grr.save(room);

    }







}
