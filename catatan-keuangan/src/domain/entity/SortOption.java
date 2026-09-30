package domain.entity;

import java.util.Comparator;

public enum SortOption {
    AMOUNT_ASC(Comparator.comparingLong(Transaction::getAmount)
            .thenComparingInt(Transaction::getId)),
    AMOUNT_DESC(Comparator.comparingLong(Transaction::getAmount).reversed()
            .thenComparingInt(Transaction::getId)),
    INCOME_FIRST(Comparator.comparing(Transaction::getType)
            .thenComparingInt(Transaction::getId)),
    EXPENSE_FIRST(Comparator.comparing(Transaction::getType, Comparator.reverseOrder())
            .thenComparingInt(Transaction::getId));

    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transaction> comparator() {
        return comparator;
    }
}
