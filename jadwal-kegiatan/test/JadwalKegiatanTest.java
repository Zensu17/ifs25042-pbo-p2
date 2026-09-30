import adapter.presenter.ActivityPresenter;
import adapter.repository.ActivityRepository;
import domain.entity.SortOption;
import framework.util.InputUtil;
import framework.view.ActivityView;
import usecase.ActivityUseCase;

public class JadwalKegiatanTest {
    private static int failed = 0;

    private static void check(String name, boolean condition) {
        System.out.println((condition ? "PASS " : "FAIL ") + name);
        if (!condition) failed++;
    }

    private static boolean throwsIae(Runnable r) {
        try { r.run(); return false; } catch (IllegalArgumentException e) { return true; }
    }

    private static String run(String... lines) {
        java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
        java.io.PrintStream out = new java.io.PrintStream(buffer);
        InputUtil input = new InputUtil(new java.util.Scanner(String.join("\n", lines) + "\n"), out);

        ActivityUseCase useCase = new ActivityUseCase(new ActivityRepository());
        new ActivityView(input, useCase, new ActivityPresenter(out)).show();
        return buffer.toString();
    }

    public static void main(String[] args) {
        ActivityUseCase useCase = new ActivityUseCase(new ActivityRepository());
        useCase.addActivity("Kuliah", "RABU", "08:00");
        useCase.addActivity("Praktikum", "senin", "13:00");
        check("judul kosong ditolak", throwsIae(() -> useCase.addActivity(" ", "Senin", "08:00")));
        check("hari kosong ditolak", throwsIae(() -> useCase.addActivity("X", "", "08:00")));
        check("ID tidak bolong setelah gagal simpan", useCase.addActivity("Olahraga", "Jumat", "06:00").getId() == 3);
        check("urut hari tidak peduli huruf besar", useCase.sortActivities(SortOption.DAY).get(0).getTitle().equals("Praktikum"));
        check("update sebagian field", useCase.updateActivity(1, null, "Selasa", null)
                && useCase.getAllActivities().get(0).getDay().equals("Selasa")
                && useCase.getAllActivities().get(0).getTitle().equals("Kuliah"));
        check("update ID tidak ada -> false", !useCase.updateActivity(99, "A", null, null));
        check("cari case-insensitive", useCase.searchActivities("KULIAH").size() == 1);
        check("view: judul kosong tampil pesan", run("1", "", "Senin", "08:00", "x").contains("[!] Judul tidak boleh kosong!"));
        System.exit(failed == 0 ? 0 : 1);
    }
}
