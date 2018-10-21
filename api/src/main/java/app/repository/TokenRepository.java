package app.repository;

import app.model.user.Token;
import app.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token,Integer> {

    Optional<Token> findByUser(User user);

    Optional<Token> findByToken(String token);
}
