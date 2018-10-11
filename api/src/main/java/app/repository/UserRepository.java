package app.repository;

import app.model.user.UserContainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserContainer,String> {

    @Query(value = "SELECT * FROM user_container WHERE user_name= ?1",nativeQuery=true)
    Optional<UserContainer> findAllUserByName(String user_name);
}
