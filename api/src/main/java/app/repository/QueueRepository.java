package app.repository;

import app.model.game.GameRoom;
import app.model.queue.QueueEntry;
import app.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface QueueRepository extends CrudRepository<QueueEntry, Integer> {

    @Query(value = "SELECT * FROM match_queue WHERE game_type=?1 AND assigned_game_uuid=NULL", nativeQuery = true)
    Optional<List<QueueEntry>> findQueryNotMatchedByType(GameRoom.GameType gameType);

    Optional<QueueEntry> findByUserAndAssignedGame(User user, GameRoom room);
}
