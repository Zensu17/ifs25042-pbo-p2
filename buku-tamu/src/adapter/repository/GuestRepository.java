package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;
import java.util.ArrayList;
import java.util.List;

public class GuestRepository implements IGuestRepository {
    private final List<Guest> data = new ArrayList<>();
    private int idCounter = 0;

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Guest save(String name, String purpose) {
        Guest guest = new Guest(++idCounter, name, purpose);
        data.add(guest);
        return guest;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(g -> g.getId() == id);
    }
}
