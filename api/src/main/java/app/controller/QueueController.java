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
import app.model.queue.JoinQueueRequest;
import app.model.queue.JoinQueueResponse;
import app.model.queue.QueueEntry;
import app.model.user.Token;
import app.model.user.User;
import app.repository.*;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import engine.State;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class QueueController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private GameRoomRepository grr;
    @Autowired
    private MoveHistoryRepository mhr;
    @Autowired
    private QueueRepository queueRepo;



    @PostMapping("/api/queue")
    public ResponseEntity<JoinQueueResponse> newGame(@RequestBody JoinQueueRequest request) {
        if(request.getGameType() == GameRoom.GameType.networkedInvited){
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Token> token = tokenRepo.findTokenByTokenStr(request.getToken());
        User user = token.get().getUser();
        if(user == null){
            return ResponseEntity.badRequest().body(null);
        }

        QueueEntry entry = new QueueEntry();
        entry.setGameType(request.getGameType());
        entry.setUser(user);

        queueRepo.save(entry);

        JoinQueueResponse response = new JoinQueueResponse();
        BeanUtils.copyProperties(entry, response);

        return ResponseEntity.ok(response);
    }



}
