package app.repository;

import app.model.game.GameRoom;
import app.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRoomRepository extends CrudRepository<GameRoom, String> {

    Optional<List<GameRoom>> findOrderByPlayerAOrPlayerB(User u);
}
