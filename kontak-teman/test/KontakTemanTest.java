import adapter.presenter.ContactPresenter;
import adapter.repository.ContactRepository;
import domain.entity.SortOption;
import framework.util.InputUtil;
import framework.view.ContactView;
import usecase.ContactUseCase;

public class KontakTemanTest {
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

        ContactUseCase useCase = new ContactUseCase(new ContactRepository());
        new ContactView(input, useCase, new ContactPresenter(out)).show();
        return buffer.toString();
    }

    public static void main(String[] args) {
        ContactUseCase useCase = new ContactUseCase(new ContactRepository());
        useCase.addContact("budi", "081", "b@x.id");
        useCase.addContact("Ani", "082", "a@x.id");
        check("nama kosong ditolak", throwsIae(() -> useCase.addContact(" ", "1", "e")));
        check("ID tidak bolong setelah gagal simpan", useCase.addContact("Citra", "083", "c@x.id").getId() == 3);
        check("urut nama A-Z tidak peduli huruf besar", useCase.sortContacts(SortOption.NAME_ASC).get(0).getName().equals("Ani"));
        check("update sebagian field", useCase.updateContact(2, "Ani Baru", null, null)
                && useCase.getAllContacts().get(1).getName().equals("Ani Baru")
                && useCase.getAllContacts().get(1).getPhone().equals("082"));
        check("update ID tidak ada -> false", !useCase.updateContact(99, "A", null, null));
        check("cari case-insensitive", useCase.searchContacts("BUDI").size() == 1);
        check("view: nama kosong tampil pesan", run("1", "", "1", "e", "x").contains("[!] Nama tidak boleh kosong!"));
        System.exit(failed == 0 ? 0 : 1);
    }
}
