package domain.entity;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/** Entity immutable: perubahan menghasilkan instance baru. */
public class Activity {
    private static final List<String> DAYS =
            List.of("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu");

    private final int id;
    private final String title;
    private final String day;
    private final String time;

    public Activity(int id, String title, String day, String time) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    /** Parameter null berarti field dipertahankan. */
    public Activity withChanges(String title, String day, String time) {
        return new Activity(
                id,
                Objects.requireNonNullElse(title, this.title),
                Objects.requireNonNullElse(day, this.day),
                Objects.requireNonNullElse(time, this.time));
    }

    /** Urutan hari dalam seminggu (Senin = 0); hari tak dikenal diurutkan paling akhir. */
    public int dayOrder() {
        int index = DAYS.indexOf(day.toLowerCase(Locale.ROOT));
        return index < 0 ? DAYS.size() : index;
    }
}
