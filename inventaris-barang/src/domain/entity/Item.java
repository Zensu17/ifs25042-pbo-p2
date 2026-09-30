package domain.entity;

/** Entity immutable: perubahan menghasilkan instance baru. */
public class Item {
    private final int id;
    private final String name;
    private final int quantity;
    private final String category;

    public Item(int id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
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
}
