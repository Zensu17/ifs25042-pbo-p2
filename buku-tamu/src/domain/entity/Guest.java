package domain.entity;

public class Guest {
    private final int id;
    private final String name;
    private final String purpose;

    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = requireText(name, "Nama tidak boleh kosong!");
        this.purpose = requireText(purpose, "Tujuan kunjungan tidak boleh kosong!");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
