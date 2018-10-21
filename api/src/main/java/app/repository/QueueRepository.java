package app.repository;

import app.model.game.GameRoom;
import app.model.queue.QueueEntry;
import app.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface QueueRepository extends CrudRepository<QueueEntry, Integer> {

    Optional<List<QueueEntry>> findBygameTypeAndAssignedGame(GameRoom.GameType gameType, GameRoom assignedGame);

    Optional<QueueEntry> findByUserAndAssignedGame(User user, GameRoom room);
}
