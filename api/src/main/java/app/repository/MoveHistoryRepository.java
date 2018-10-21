package app.repository;

import app.model.game.GameRoom;
import app.model.move.MoveHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoveHistoryRepository extends CrudRepository<MoveHistory, Integer> {

    @Query(value = "SELECT * FROM move_history_new WHERE game_id= ?1 ORDER BY id DESC LIMIT 1;", nativeQuery=true)
    Optional<MoveHistory> findLastHistoryByGameId(String gameId);

    Optional<List<MoveHistory>> findByGame(GameRoom gameRoom);

}
