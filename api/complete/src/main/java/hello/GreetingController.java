package hello;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.core.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//POST /game/availableMove
//Body:
//{
//	state: string,
//	from: integer
//}
//Response:
//{
//	from: integer,
//	availableMove: [integer, integer, integer ….]
//}

@CrossOrigin
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @CrossOrigin(origins = "*")
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/move",method=RequestMethod.POST)
    public Move move(@RequestBody Move info){
        String initState = info.getState();
        int initPos = info.getStart();
        int finalPos = info.getEnd();
        String State_str_format = initState.replaceAll("8","xxxxxxxx");
        char[] char_array_version = State_str_format.toCharArray();
        if (initPos % 8 == 0){
            initPos += 1;
        }
        if(finalPos % 8 == 0){
            finalPos += 1;
        }
        char role = char_array_version[initPos];
        char_array_version[initPos] = 'x';
        char_array_version[finalPos] = role;
        String result = String.valueOf(char_array_version);
        result = result.replaceAll("x","1");
        String returnMe = "";
        for(int i = 0; i <8; ++i){
            returnMe += processString(result.split("/")[i]);
            returnMe += "/";
        }
        returnMe = returnMe.replaceAll("0","");
        returnMe = returnMe.substring(0,returnMe.length()-1);
        System.out.println(returnMe);
        return new Move(returnMe,0,0);
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

    @PostMapping(path="/addState")
    public String addStateToDb(@RequestBody MoveHistory mh){
        mhr.save(mh);
        return "Success";
    }

    @GetMapping("/getHistory")
    public Iterable<MoveHistory> getAllMoveHistory(){
        return mhr.findAll();
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
