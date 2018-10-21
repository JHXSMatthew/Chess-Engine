package app.controller;

import app.model.user.LoginRequest;
import app.model.user.Token;
import app.model.user.User;
import app.repository.TokenRepository;
import app.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by JHXSMatthew on 13/10/18.
 */
@CrossOrigin
@RestController
@Api(value = "auth controller",description = "handle the login by token")
public class AuthController {


    @Autowired
    private TokenRepository tokenRepo;

    @Autowired
    private UserRepository userRepo;



    @PostMapping("/api/auth")
    @ApiOperation(value = "get a token")
    public ResponseEntity<Token> post(@RequestBody LoginRequest request) {
        Optional<User> u = userRepo.findAllUserByName(request.getUserName());
        if(u.isPresent()){
            if(u.get().getPassword().equals(request.getPassword())){
                Optional<Token> token = tokenRepo.findByUser(u.get());
                if(token.isPresent()){
                    return ResponseEntity.ok(token.get());
                }else{
                    Token t = new Token();
                    t.setToken(UUID.randomUUID().toString());
                    t.setUser(u.get());
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

    @ApiOperation(value = "delete the token")
    @DeleteMapping("/api/auth")
    public ResponseEntity delete(@RequestBody Token token) {
       Optional<Token> t = tokenRepo.findByToken(token.getToken());
       if(t.isPresent()){
           tokenRepo.delete(t.get());
           return ResponseEntity.accepted().body(null);
       }else{
            return ResponseEntity.badRequest().body(null);
       }
    }
}
