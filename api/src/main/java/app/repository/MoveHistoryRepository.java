package app.repository;

import app.model.move.MoveHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveHistoryRepository extends CrudRepository<MoveHistory, Integer> {
}
