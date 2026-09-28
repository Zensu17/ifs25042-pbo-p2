package usecase;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GuestUseCase {
    private final IGuestRepository repository;

    public GuestUseCase(IGuestRepository repository) {
        this.repository = repository;
    }

    public List<Guest> getAllGuests() {
        return repository.findAll();
    }

    public Guest registerGuest(String name, String purpose) {
        return repository.save(name, purpose);
    }

    public boolean removeGuest(int id) {
        return repository.deleteById(id);
    }

    public List<Guest> searchGuests(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
            .filter(g -> g.getName().toLowerCase(Locale.ROOT).contains(lower))
            .collect(Collectors.toList());
    }
}
