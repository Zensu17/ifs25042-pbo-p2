package domain.entity;

/** Entity immutable: perubahan menghasilkan instance baru. */
public class Item {
    private final int id;
    private final String name;
    private final int quantity;
    private final String category;

    public Item(int id, String name, int quantity, String category) {
        this.id = id;
        this.name = requireText(name, "Nama barang tidak boleh kosong!");
        if (quantity < 0) {
            throw new IllegalArgumentException("Jumlah stok tidak boleh negatif!");
        }
        this.quantity = quantity;
        this.category = requireText(category, "Kategori tidak boleh kosong!");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public Item withQuantity(int quantity) {
        return new Item(id, name, quantity, category);
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
