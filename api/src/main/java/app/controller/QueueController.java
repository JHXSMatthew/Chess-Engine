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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@Api(value = "Queue controller",description = "Handle the Queue actions")
public class QueueController {


    @Autowired
    private TokenRepository tokenRepo;
    @Autowired
    private QueueRepository queueRepo;


    @ApiOperation(value="join the queue")
    @PostMapping("/api/queue")
    public ResponseEntity<JoinQueueResponse> post(@RequestBody JoinQueueRequest request) {
        if(request.getGameType() == GameRoom.GameType.networkedInvited){
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Token> token = tokenRepo.findByToken(request.getToken());
        if(!token.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        User user = token.get().getUser();
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        QueueEntry entry ;


        Optional<QueueEntry> mayExsit = queueRepo.findByUserAndAssignedGame(user, null);
        if(mayExsit.isPresent()){
           mayExsit.get().setGameType(request.getGameType());
           entry = mayExsit.get();
        }else{
            entry = new QueueEntry();
            entry.setGameType(request.getGameType());
            entry.setUser(user);
        }

        queueRepo.save(entry);

        JoinQueueResponse response = new JoinQueueResponse();
        BeanUtils.copyProperties(entry, response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/queue/{id}")
    @ApiOperation(value = "get current queue info")
    public ResponseEntity<QueueEntry> get(@PathVariable int id, @RequestParam String token){
        Optional<Token> t = tokenRepo.findByToken(token);
        if(!t.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<QueueEntry> entry = queueRepo.findById(id);
        if(!entry.isPresent()){
            return ResponseEntity.notFound().build();
        }

        if(entry.get().getUser() != t.get().getUser()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(entry.get());

    }


    @DeleteMapping("/api/queue/{id}")
    public ResponseEntity delete(@PathVariable int id,@RequestParam String token){
        Optional<Token> t = tokenRepo.findByToken(token);
        if(!t.isPresent()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<QueueEntry> entry = queueRepo.findById(id);
        if(entry.isPresent()){
            tokenRepo.delete(t.get());
        }

        return ResponseEntity.ok().build();
    }


}
