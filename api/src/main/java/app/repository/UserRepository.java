package app.repository;

import app.model.user.UserContainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserContainer,String> {
}
