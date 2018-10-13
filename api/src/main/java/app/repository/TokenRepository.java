package app.repository;

import app.model.user.Token;
import app.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token,Integer> {

    @Query(value = "SELECT * FROM auth_token WHERE user_Id= ?1",nativeQuery=true)
    Optional<Token> findTokenByUserId(int userId);

    @Query(value = "SELECT * FROM auth_token WHERE token= ?1",nativeQuery=true)
    Optional<Token> findTokenByTokenStr(String tokenStr);
}
