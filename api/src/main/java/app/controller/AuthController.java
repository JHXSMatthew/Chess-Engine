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
import app.model.user.LoginRequest;
import app.model.user.Token;
import app.model.user.User;
import app.repository.TokenRepository;
import app.repository.UserRepository;
import engine.State;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by JHXSMatthew on 13/10/18.
 */
@CrossOrigin
@RestController
public class AuthController {


    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private UserRepository userRepo;



    @PostMapping("/api/auth")
    public ResponseEntity<Token> post(@RequestBody LoginRequest request) {
        Optional<User> u = userRepo.findAllUserByName(request.getUserName());
        if(u.isPresent()){
            if(u.get().getPassword().equals(request.getPassword())){
                Optional<Token> token = tokenRepo.findTokenByUserId(u.get().getUserId());
                if(token.isPresent()){
                    return ResponseEntity.ok(token.get());
                }else{
                    Token t = new Token();
                    t.setToken(UUID.randomUUID().toString());
                    t.setUserId(u.get().getUserId());
                    tokenRepo.save(t);
                    return ResponseEntity.ok(t);
                }
            }else{
                return ResponseEntity.badRequest().body(null);
            }
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/api/auth")
    public ResponseEntity delete(@RequestBody Token token) {
       Optional<Token> t = tokenRepo.findTokenByTokenStr(token.getToken());
       if(t.isPresent()){
           tokenRepo.delete(t.get());
           return ResponseEntity.accepted().body(null);
       }else{
            return ResponseEntity.badRequest().body(null);
       }
    }
}
