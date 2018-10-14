package app.repository;

import app.model.queue.QueueEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QueueRepository extends CrudRepository<QueueEntry, Integer> {


}
