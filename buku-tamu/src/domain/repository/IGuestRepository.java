package domain.repository;

import domain.entity.Guest;
import java.util.List;

public interface IGuestRepository {
    List<Guest> findAll();

    Guest save(String name, String purpose);

    boolean deleteById(int id);
}
