package controller;

import engine.ChessEngineDummy;
import engine.ChessEngineI;
import model.StateContainer;
import model.game.GameInfoResponse;
import model.game.GameRoom;
import model.game.JoinGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.GameRoomRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@CrossOrigin
@RestController
public class GameController {

    private static ChessEngineI engine = new ChessEngineDummy();

    @Autowired
    private GameRoomRepository grr;
    private Integer id = 1;

    @CrossOrigin(origins = "*")
    @PostMapping("/game")
    public GameRoom newGame() {
        String initState = engine.getInitState();
        UUID gameId = UUID.randomUUID();
        GameRoom gr = new GameRoom();
        gr.setId(id);
        id += 1;
        gr.setRoomId(gameId.toString());
        gr.setNumOfUser(1);
        gr.setState(initState);
        gr.setStatus("lobby");
        grr.save(gr);
        return gr;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/game/{id}")
    // Get status and state
    public GameInfoResponse getGameInfo(@RequestParam UUID id) {
        GameInfoResponse info = new GameInfoResponse();
        Iterable<GameRoom> result = grr.findAll();
        for (GameRoom i : result) {
            if (i.getRoomId().equals(id.toString())) {
                info.setState(i.getState());
                info.setStatus(i.getStatus());
            }
        }
        return info;
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/game/{id}?move={move}")
    public StateContainer handlePatch(@RequestParam UUID id, @RequestParam String move) {
        Iterable<GameRoom> result = grr.findAll();
        // Assume move is : "from to"
        int from = Integer.parseInt(move.split(" ")[0]);
        int to = Integer.parseInt(move.split(" ")[1]);
        String resultState = "";
        for (GameRoom i : result) {
            if (i.getRoomId().equals(id.toString())) {
                resultState = engine.move(i.getState(), from, to);
                i.setState(resultState);
            }
        }
        StateContainer finalState = new StateContainer();
        finalState.setState(resultState);
        return finalState;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/game/{id}")
    public JoinGameResponse handlePutAction(@RequestParam UUID id) {
        Iterable<GameRoom> result = grr.findAll();
        for (GameRoom i : result) {
            if (i.getRoomId().equals(id.toString())) {
                i.setNumOfUser(2);
                i.setStatus("start");
            }
        }
        List<String> playerType = Arrays.asList("white", "black");
        String returnType = playerType.get(new Random().nextInt(playerType.size()));
        return new JoinGameResponse(returnType);
    }
}
