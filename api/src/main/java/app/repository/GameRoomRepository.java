package app.repository;

import app.model.game.GameRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRoomRepository extends CrudRepository<GameRoom, UUID> {

}
