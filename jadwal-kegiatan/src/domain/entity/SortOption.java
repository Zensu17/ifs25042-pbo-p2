package domain.entity;

import java.util.Comparator;

public enum SortOption {
    DAY(Comparator.comparingInt(Activity::dayOrder)
            .thenComparing(Activity::getTime)),
    TIME(Comparator.comparing(Activity::getTime)),
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Activity> comparator() {
        return comparator;
    }
}
