package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.repository.IItemRepository;
import java.util.List;
import java.util.Locale;

public class ItemUseCase {
    private final IItemRepository repository;

    public ItemUseCase(IItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getAllItems() {
        return repository.findAll();
    }

    public Item addItem(String name, int quantity, String category) {
        return repository.save(name, quantity, category);
    }

    public boolean removeItem(int id) {
        return repository.deleteById(id);
    }

    /** {@code quantity} null berarti stok tidak diubah. @return true jika barang ditemukan */
    public boolean updateStock(int id, Integer quantity) {
        return repository.findById(id)
                .map(item -> quantity == null ? item : item.withQuantity(quantity))
                .map(repository::update)
                .orElse(false);
    }

    public List<Item> searchItems(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(i -> i.getName().toLowerCase(Locale.ROOT).contains(lower))
                .toList();
    }

    public List<Item> sortItems(SortOption option) {
        return repository.findAll().stream().sorted(option.comparator()).toList();
    }
}
