package app.controller;

import app.model.user.UserContainer;
import app.model.user.UserRequestResponse;
import app.repository.UserRepository;
import engine.ChessEngineDummy;
import engine.ChessEngineI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
