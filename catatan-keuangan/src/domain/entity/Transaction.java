package domain.entity;

public class Transaction {
    private final int id;
    private final String description;
    private final long amount;
    private final TransactionType type;

    public Transaction(int id, String description, long amount, TransactionType type) {
        this.id = id;
        this.description = requireText(description, "Keterangan tidak boleh kosong!");
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0!");
        }
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
