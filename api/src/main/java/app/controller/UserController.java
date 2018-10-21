package app.controller;

import app.exception.IllegalStateExceptionInternal;
import app.model.user.Token;
import app.model.user.User;
import app.model.user.UserResponseModel;
import app.repository.TokenRepository;
import app.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@Api(value="User System",description="Handle all the user actions")
public class UserController {

    @Autowired
    private UserRepository ur;

    @Autowired
    private TokenRepository tokenRepo;

    @ApiOperation(value="Make a new User")
    @PostMapping("/api/user")
    public void post(@RequestBody User newUser){
        //validation
        if(newUser.getUserName() == null
                || newUser.getPassword() == null){
            throw new IllegalStateExceptionInternal();
        }

        ur.save(newUser);
    }

    @ApiOperation(value="Get user's information by user's id")
    @GetMapping("api/user/{id}")
    public ResponseEntity<UserResponseModel> get(@PathVariable int id, @RequestParam String token){
        Optional<User> user = ur.findById(id);
        if(!user.isPresent()){
            return ResponseEntity.badRequest().build();
        }

        Optional<Token> t = tokenRepo.findByUser(user.get());
        if(t.isPresent()){
            if(t.get().getToken().equals(token)){
                UserResponseModel responseModel = new UserResponseModel();
                BeanUtils.copyProperties(user.get(), responseModel);
                return ResponseEntity.ok(responseModel);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

            }
        }else{
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PutMapping("api/user/{userName}")
    @ApiOperation(value="update user's information by user name")
    public ResponseEntity put(@PathVariable String userName, @RequestParam String password,
                                          @RequestParam String newPassword){
        Optional<User> dbModel = ur.findAllUserByName(userName);
        if(dbModel.isPresent()){
            if(dbModel.get().getPassword().equals(password)){
                dbModel.get().setPassword(newPassword);
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
