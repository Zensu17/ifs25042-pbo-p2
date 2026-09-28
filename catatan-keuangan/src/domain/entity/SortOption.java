package domain.entity;

public enum SortOption {
    AMOUNT_ASC("Jumlah terkecil"),
    AMOUNT_DESC("Jumlah terbesar"),
    INCOME_FIRST("Pemasukan dulu"),
    EXPENSE_FIRST("Pengeluaran dulu");

    private final String label;

    SortOption(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static SortOption fromChoice(String choice) {
        switch (choice) {
            case "1":
                return AMOUNT_ASC;
            case "2":
                return AMOUNT_DESC;
            case "3":
                return INCOME_FIRST;
            case "4":
                return EXPENSE_FIRST;
            default:
                return null;
        }
    }
}
