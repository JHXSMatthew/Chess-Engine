package app.repository;

import app.model.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    @Query(value = "SELECT * FROM user WHERE user_name= ?1",nativeQuery=true)
    Optional<User> findAllUserByName(String userName);
}
