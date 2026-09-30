import adapter.presenter.ItemPresenter;
import adapter.repository.ItemRepository;
import domain.entity.SortOption;
import framework.util.InputUtil;
import framework.view.ItemView;
import usecase.ItemUseCase;

public class InventarisBarangTest {
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

        ItemUseCase useCase = new ItemUseCase(new ItemRepository());
        new ItemView(input, useCase, new ItemPresenter(out)).show();
        return buffer.toString();
    }

    public static void main(String[] args) {
        ItemUseCase useCase = new ItemUseCase(new ItemRepository());
        useCase.addItem("Mouse", 10, "Elektronik");
        useCase.addItem("Keyboard", 5, "Elektronik");
        check("nama kosong ditolak", throwsIae(() -> useCase.addItem(" ", 1, "X")));
        check("kategori kosong ditolak", throwsIae(() -> useCase.addItem("Y", 1, "")));
        check("ID tidak bolong setelah gagal simpan", useCase.addItem("Monitor", 3, "Elektronik").getId() == 3);
        check("updateStock mengganti nilai", useCase.updateStock(1, 25) && useCase.getAllItems().get(0).getQuantity() == 25);
        check("updateStock null = tidak berubah", useCase.updateStock(2, null) && useCase.getAllItems().get(1).getQuantity() == 5);
        check("updateStock ID tidak ada -> false", !useCase.updateStock(99, 1));
        check("urutan tetap setelah update", useCase.getAllItems().get(0).getName().equals("Mouse"));
        check("cari case-insensitive", useCase.searchItems("KEY").size() == 1);
        check("urut jumlah terkecil", useCase.sortItems(SortOption.QUANTITY_ASC).get(0).getName().equals("Monitor"));
        check("view: 'x' di Jumlah Baru = batal", !run("1", "A", "1", "K", "2", "1", "x", "x").contains("Berhasil mengubah"));
        check("view: nama kosong tampil pesan", run("1", "", "5", "K", "x").contains("[!] Nama barang tidak boleh kosong!"));
        System.exit(failed == 0 ? 0 : 1);
    }
}
