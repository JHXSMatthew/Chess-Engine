package app.controller;

import app.model.user.UserContainer;
import app.model.user.UserRequestResponse;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserRepository ur;
    @PostMapping("/api/user/register")
    // JSON Format: {"userName": "test","password": "pass","email": "test@gmail.com"}
    public UserRequestResponse handlePostRegister(@RequestBody UserContainer newUser){
        ur.save(newUser);
        UserRequestResponse newUserResponse = new UserRequestResponse();
        newUserResponse.setUserName(newUser.getUserName());
        newUserResponse.setEmail(newUser.getEmail());

        return newUserResponse;
    }

    // get email
    @GetMapping("/api/user/{userName}")
    public String handleGetInfo(@PathVariable String userName){
        Optional<UserContainer> dbModel = ur.findAllUserByName(userName);
        return dbModel.map(UserContainer::getEmail).orElse(null);
    }


    // change password
    // using form-data
    @PostMapping("api/user/forget/{userName}")
    public String handleResetPost(@PathVariable String userName,@RequestParam String newPassWord){
        Optional<UserContainer> dbModel = ur.findAllUserByName(userName);
        if(dbModel.isPresent()){
            dbModel.get().setPassword(newPassWord);
            ur.save(dbModel.get());
            return "Success";
        }else{
            return "Failed";
        }
    }

    // Log in
    @PostMapping("api/user/login/")
    public String handlePostLogin(@RequestParam("userName") String UserName,@RequestParam("passWord") String password){
        return null;
    }

}
