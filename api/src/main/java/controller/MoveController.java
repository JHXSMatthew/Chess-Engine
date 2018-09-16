package controller;

import java.util.ArrayList;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import model.AvailableMoveRequestModel;
import model.AvailableMoveResponseModel;
import model.MoveRequestModel;
import model.StateContainer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class MoveController {

    private static ChessEngineI engine = new ChessEngineDummy();


    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/move",method=RequestMethod.POST)
    public StateContainer handleMove(@RequestBody MoveRequestModel info){
        String returnValue = engine.move(info.getState(), info.getFrom(), info.getTo());
        StateContainer container = new StateContainer();
        container.setState(returnValue);
        return container;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/move/available", method=RequestMethod.POST)
    public AvailableMoveResponseModel handleAvailableMove(@RequestBody AvailableMoveRequestModel am){
        //TODO: use api
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
        return new AvailableMoveResponseModel(currentPos,result);
    }

    private String processString(String s){
        String result = "";
        int csum = 0;
        char[] arrayVersion = s.toCharArray();
        for(int i = 0; i < s.length(); ++i){
            if(arrayVersion[i] == '1'){
                csum += 1;
                continue;
            } else {
                result += csum;
                csum = 0;
            }
            result += arrayVersion[i];
        }
        if(csum > 0){
            result += csum;
        }
        return result;
    }
}
