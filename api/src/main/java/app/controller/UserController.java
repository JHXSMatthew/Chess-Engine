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
    public UserRequestResponse register(@RequestBody UserContainer newUser){
        ur.save(newUser);
        UserRequestResponse newUserResponse = new UserRequestResponse();
        newUserResponse.setUserName(newUser.getUserName());
        newUserResponse.setEmail(newUser.getEmail());

        return newUserResponse;
    }

    // get email
    @GetMapping("/api/user/{userName}")
    public String getEmail(@PathVariable String userName){
        Optional<UserContainer> dbModel = ur.findById(userName);
        return dbModel.map(UserContainer::getEmail).orElse(null);
    }


    // change password
    @PostMapping("api/user/forget/{userName}")
    public String changePassword(@PathVariable String userName,@RequestParam String newPass){
        Optional<UserContainer> dbModel = ur.findById(userName);
        if(dbModel.isPresent()){
            dbModel.get().setPassword(newPass);
            ur.save(dbModel.get());
            return "Success";
        }else{
            return "Failed";
        }
    }

    // Log in
    @PostMapping("api/user/login/")
    public String handleLogin(@RequestParam("userName") String UserName,@RequestParam("passWord") String password){

        return null;
    }

}
