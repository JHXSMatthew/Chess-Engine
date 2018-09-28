package app.controller;

import app.model.move.MoveHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import app.repository.MoveHistoryRepository;


@RestController
public class MoveHistoryController {

    @Autowired
    private MoveHistoryRepository mhr;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/moveHistory")
    public String addStateToDb(@RequestBody MoveHistory mh) {
        mhr.save(mh);
        return "Success";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/moveHistory")
    public Iterable<MoveHistory> getAllMoveHistory() {
        return mhr.findAll();
    }

}

  
