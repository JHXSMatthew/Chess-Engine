package hello.repository;

import hello.model.MoveHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveHistoryRepository extends CrudRepository<MoveHistory, Integer>{
}
