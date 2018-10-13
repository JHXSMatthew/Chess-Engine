package app.controller;

import app.exception.IllegalStateExceptionInternal;
import app.model.user.User;
import app.model.user.CreateUserRequestResponse;
import app.repository.UserRepository;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepository ur;


    @PostMapping("/api/user")
    public void handlePostRegister(@RequestBody User newUser){
        //validation
        if(newUser.getUserName() == null
                || newUser.getPassword() == null){
            throw new IllegalStateExceptionInternal();
        }

        ur.save(newUser);
    }


    @PutMapping("api/user/{userName}")
    public ResponseEntity handleResetPost(@PathVariable String userName, @RequestParam String password,
                                          @RequestBody String newPassWord){
        Optional<User> dbModel = ur.findAllUserByName(userName);
        if(dbModel.isPresent()){
            if(dbModel.get().getPassword().equals(password)){
                dbModel.get().setPassword(newPassWord);
                ur.save(dbModel.get());
                return ResponseEntity.ok(null);
            }else{
                return ResponseEntity.badRequest().body(null);
            }

        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }

}
