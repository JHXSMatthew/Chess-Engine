package app.controller;

import app.exception.IllegalStateExceptionInternal;
import app.model.user.Token;
import app.model.user.User;
import app.model.user.UserResponseModel;
import app.repository.TokenRepository;
import app.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class RankingController {

    @Autowired
    private UserRepository ur;


    @GetMapping("api/ranking")
    public ResponseEntity<List<User>> get(){
        Iterable<User> list = ur.findAll();
        List<User> l = new ArrayList();
        for(User u : list){
            l.add(u);
        }
        Collections.sort(l, User.comparator);

        return ResponseEntity.ok(l);
    }


}
