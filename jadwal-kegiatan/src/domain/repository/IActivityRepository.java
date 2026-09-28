package domain.repository;

import domain.entity.Activity;
import java.util.List;
import java.util.Optional;

public interface IActivityRepository {
    List<Activity> findAll();

    Optional<Activity> findById(int id);

    Activity save(String title, String day, String time);

    boolean deleteById(int id);

    void update(Activity activity);
}
