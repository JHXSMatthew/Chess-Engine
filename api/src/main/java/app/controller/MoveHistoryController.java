package app.controller;

import app.model.move.MoveHistory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.repository.MoveHistoryRepository;


@RestController
@Api(value = "Move history controller",description = "Handle all the history move")
public class MoveHistoryController {

    @Autowired
    private MoveHistoryRepository mhr;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/moveHistory")
    @ApiOperation(value="add the move history into db")
    public String addStateToDb(@RequestBody MoveHistory mh) {
        mhr.save(mh);
        return "Success";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/moveHistory")
    @ApiOperation(value="get all the move history")
    public Iterable<MoveHistory> getAllMoveHistory() {
        return mhr.findAll();
    }

}

  
