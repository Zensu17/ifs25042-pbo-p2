package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepository implements IItemRepository {
    private final List<Item> data = new ArrayList<>();
    private int nextId = 1;

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Item> findById(int id) {
        return data.stream().filter(i -> i.getId() == id).findFirst();
    }

    @Override
    public Item save(String name, int quantity, String category) {
        Item item = new Item(nextId++, name, quantity, category);
        data.add(item);
        return item;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(i -> i.getId() == id);
    }

    @Override
    public boolean update(Item item) {
        for (int index = 0; index < data.size(); index++) {
            if (data.get(index).getId() == item.getId()) {
                data.set(index, item);
                return true;
            }
        }
        return false;
    }
}
