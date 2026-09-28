package domain.entity;

import java.util.List;

public class Activity {
    private static final List<String> DAYS =
            List.of("senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu");

    private final int id;
    private String title;
    private String day;
    private String time;

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

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDay(String day) {
        this.day = day;
    }

    public void changeTime(String time) {
        this.time = time;
    }

    /** Urutan hari dalam seminggu (Senin = 0); hari tak dikenal diurutkan paling akhir. */
    public int dayOrder() {
        int index = DAYS.indexOf(day.toLowerCase());
        return index < 0 ? DAYS.size() : index;
    }
}
