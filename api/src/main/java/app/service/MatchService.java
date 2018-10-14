package app.service;

import app.model.game.GameRoom;
import app.model.game.JoinGameResponse;
import app.model.queue.QueueEntry;
import app.repository.GameRoomRepository;
import app.repository.QueueRepository;
import app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import utilities.PairU;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by JHXSMatthew on 14/10/18.
 */
@Component
public class MatchService {


    @Autowired
    private QueueRepository queueRepo;
    @Autowired
    private GameRoomRepository gameRepo;

    @Autowired
    private UserRepository ur;


    private static final Logger log = LoggerFactory.getLogger(MatchService.class);

    @Scheduled(fixedRate = 1000)
    public void run(){

        for(GameRoom.GameType t : GameRoom.GameType.values()){
            //skip network invited
            if(t == GameRoom.GameType.networkedInvited){
                continue;
            }


            //find all requests
            Optional<List<QueueEntry>> entryList = queueRepo.findQueryNotMatchedByType(t);

            if(!entryList.isPresent() || entryList.get().size() == 1) {
                continue;
            }

            //match pair
            PairU<QueueEntry, QueueEntry> matchPair = null;
            List<QueueEntry> listToMatch = entryList.get();

            //sort by MMR if ranked game
            if(t == GameRoom.GameType.rank){
                Collections.sort(listToMatch, new MMRComparator());
            }


            while(entryList.get().size() > 1)
            {
                //match will remove 2 element in the entry list
                matchPair = simpleMatch(listToMatch);

                //create game instance
                GameRoom room = new GameRoom();
                room.setGameType(t);
                room.setNumOfUser(2);
                room.setStatus(GameRoom.GameStatus.ingame);
                room.setPlayerA(matchPair.fst().getUser());
                room.setPlayerB(matchPair.snd().getUser());
                //save to db
                gameRepo.save(room);


                //update queue state
                matchPair.fst().setAssignedGame(room);
                matchPair.snd().setAssignedGame(room);
                matchPair.fst().setPlayerType(JoinGameResponse.PlayerType.b);
                matchPair.snd().setPlayerType(JoinGameResponse.PlayerType.w);
                //save to db
                queueRepo.save(matchPair.fst());
                queueRepo.save(matchPair.snd());

                log.info("[" + t.toString() +  "] Match " + matchPair.fst().getUser().getUserName()
                        + " with " + matchPair.snd().getUser().getUserName());

            }


        }

    }


    /***
     * @precondition: entry.size > 2
     * @param entry
     * @return
     */
    private PairU<QueueEntry, QueueEntry> simpleMatch(List<QueueEntry> entry){
        QueueEntry a = entry.remove(0);
        QueueEntry b = entry.remove(0);
        return new PairU<>(a,b);
    }

    class MMRComparator implements Comparator<QueueEntry>
    {

        @Override
        public int compare(QueueEntry o1, QueueEntry o2) {
            return o1.getUser().getMMR() - o2.getUser().getMMR();
        }
    }
}
