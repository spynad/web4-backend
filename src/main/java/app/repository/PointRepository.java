package app.repository;

import app.model.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface PointRepository extends CrudRepository<Point, Long> {
    Collection<Point> findAllByUserId(long user_id);

    @Transactional
    void deleteAllByUserId(long user_id);
}
