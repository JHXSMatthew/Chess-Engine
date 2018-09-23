package hello.repository;
import hello.model.GameRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRoomRepository extends CrudRepository<GameRoom, Integer> {
}
