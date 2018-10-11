package app.controller;

import app.model.user.UserContainer;
import app.model.user.UserRequestResponse;
import app.repository.UserRepository;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {
    private static ChessEngineI engine = new ChessEngineDummy();

    @Autowired
    private UserRepository ur;
    @PostMapping("/api/user")
    public UserRequestResponse newUser(){
        String GameId = "";
        String PlayerType = "";
        String UserName = "test";
        String PassWord = "password";

        UserContainer newUser = new UserContainer(GameId,PlayerType,UserName,PassWord);

        ur.save(newUser);

        UserRequestResponse newUserResponse = new UserRequestResponse();
        newUserResponse.setGameId(GameId);
        newUserResponse.setPassWord(PassWord);
        newUserResponse.setPlayerType(PlayerType);
        newUserResponse.setUserName(UserName);

        return newUserResponse;
    }

    // User join the game can change to readable representation
    @PutMapping("/api/user/{userName}/{gameId}/{type}")
    public void JoinTheGame(@PathVariable String username, @PathVariable String id,
                            @PathVariable String type){
        Optional<UserContainer> dbModel = ur.findById(username);
        if(dbModel.isPresent()){
            dbModel.get().setGameId(id);
            dbModel.get().setPlayerType(type);
            ur.save(dbModel.get());
        }
    }

    // get the password
    @GetMapping("/api/user/{userName}")
    public String getPassword(@PathVariable String userName){
        Optional<UserContainer> dbModel = ur.findById(userName);
        return dbModel.map(UserContainer::getPassword).orElse(null);
    }

    // reset password
    @PostMapping("api/user/forget/{userName}")
    public String resetPassword(@PathVariable String userName,@RequestParam String newPass){
        Optional<UserContainer> dbModel = ur.findById(userName);
        if(dbModel.isPresent()){
            dbModel.get().setPassword(newPass);
            ur.save(dbModel.get());
            return "Success";
        }else{
            return "Failed";
        }
    }
    
}
