import adapter.presenter.GuestPresenter;
import adapter.repository.GuestRepository;
import framework.util.InputUtil;
import framework.view.GuestView;
import usecase.GuestUseCase;

public class BukuTamuTest {
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

        GuestUseCase useCase = new GuestUseCase(new GuestRepository());
        new GuestView(input, useCase, new GuestPresenter(out)).show();
        return buffer.toString();
    }

    public static void main(String[] args) {
        GuestUseCase useCase = new GuestUseCase(new GuestRepository());
        useCase.registerGuest("Budi", "Rapat");
        check("nama kosong ditolak", throwsIae(() -> useCase.registerGuest(" ", "Rapat")));
        check("tujuan kosong ditolak", throwsIae(() -> useCase.registerGuest("Ani", "")));
        check("ID tidak bolong setelah gagal simpan", useCase.registerGuest("Ani", "Tamu").getId() == 2);
        check("cari case-insensitive", useCase.searchGuests("BUDI").size() == 1);
        check("hapus berhasil lalu gagal", useCase.removeGuest(1) && !useCase.removeGuest(1));
        check("view: nama kosong tampil pesan", run("1", "", "Rapat", "x").contains("[!] Nama tidak boleh kosong!"));
        check("view: EOF tidak crash", run("1", "Budi").contains("Mendaftarkan Tamu"));
        System.exit(failed == 0 ? 0 : 1);
    }
}
