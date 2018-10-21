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

    Optional<MoveHistory> findFirstByGameOrderByIdDesc(GameRoom gameRoom);

    Optional<List<MoveHistory>> findByGame(GameRoom gameRoom);

}
