package hello;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.UUID;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import engine.*;

@CrossOrigin
@RestController
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/move",method=RequestMethod.POST)
    public Move handleMove(@RequestBody Move info){
        String returnValue = engine.move(info.getState(), info.getStart(), info.getEnd());
        Move container = new Move();
        container.setState(returnValue);
        return container;
    }

    //rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/availableMove", method=RequestMethod.POST)
    public allAvailable move(@RequestBody availableMove am){
        String initState = am.getState();
        int currentPos = am.getFrom();
        ArrayList<Integer> result = new ArrayList<>();
        // I've no idea about the role, so I assume currently it can move to anywhere
        for(int i = 0; i < 64; ++i){
            if(i != currentPos){
                result.add(i);
            }
        }
        System.out.println(result);
        return new allAvailable(currentPos,result);
    }

    @Autowired
    private MoveHistoryRepository mhr;

    @CrossOrigin(origins = "*")
    @PostMapping(path="/addState")
    public String addStateToDb(@RequestBody MoveHistory mh){
        mhr.save(mh);
        return "Success";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getHistory")
    public Iterable<MoveHistory> getAllMoveHistory(){
        return mhr.findAll();
    }



}
