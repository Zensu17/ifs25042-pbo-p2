package usecase;

import domain.entity.Activity;
import domain.entity.SortOption;
import domain.repository.IActivityRepository;
import java.util.List;
import java.util.Locale;

public class ActivityUseCase {
    private final IActivityRepository repository;

    public ActivityUseCase(IActivityRepository repository) {
        this.repository = repository;
    }

    public List<Activity> getAllActivities() {
        return repository.findAll();
    }

    public Activity addActivity(String title, String day, String time) {
        return repository.save(title, day, time);
    }

    public boolean removeActivity(int id) {
        return repository.deleteById(id);
    }

    /** Parameter null berarti field tidak diubah. @return true jika kegiatan ditemukan */
    public boolean updateActivity(int id, String title, String day, String time) {
        return repository.findById(id)
                .map(activity -> activity.withChanges(title, day, time))
                .map(repository::update)
                .orElse(false);
    }

    public List<Activity> searchActivities(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(a -> a.getTitle().toLowerCase(Locale.ROOT).contains(lower))
                .toList();
    }

    public List<Activity> sortActivities(SortOption option) {
        return repository.findAll().stream().sorted(option.comparator()).toList();
    }
}
