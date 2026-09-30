import adapter.presenter.FinancePresenter;
import adapter.repository.TransactionRepository;
import domain.entity.SortOption;
import domain.entity.TransactionType;
import framework.util.InputUtil;
import framework.view.FinanceView;
import usecase.FinanceUseCase;

public class CatatanKeuanganTest {
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

        FinanceUseCase useCase = new FinanceUseCase(new TransactionRepository());
        new FinanceView(input, useCase, new FinancePresenter(out)).show();
        return buffer.toString();
    }

    public static void main(String[] args) {
        FinanceUseCase useCase = new FinanceUseCase(new TransactionRepository());
        useCase.addTransaction("Gaji", 100, TransactionType.INCOME);
        useCase.addTransaction("Makan", 30, TransactionType.EXPENSE);
        check("saldo = pemasukan - pengeluaran", useCase.getBalance() == 70);
        check("keterangan kosong ditolak", throwsIae(() -> useCase.addTransaction(" ", 5, TransactionType.INCOME)));
        check("jumlah <= 0 ditolak", throwsIae(() -> useCase.addTransaction("X", 0, TransactionType.INCOME)));
        check("ID tidak bolong setelah gagal simpan", useCase.addTransaction("Y", 1, TransactionType.INCOME).getId() == 3);
        check("cari case-insensitive", useCase.searchTransactions("GAJI").size() == 1);
        check("urut jumlah terbesar", useCase.sortTransactions(SortOption.AMOUNT_DESC).get(0).getAmount() == 100);
        check("pengeluaran dulu", useCase.sortTransactions(SortOption.EXPENSE_FIRST).get(0).getDescription().equals("Makan"));
        check("hapus ID tidak ada -> false", !useCase.removeTransaction(99));
        check("view: 'x' di prompt jumlah = batal", !run("1", "Gaji", "x", "x").contains("Jumlah tidak valid"));
        check("view: jumlah 'abc' invalid", run("1", "Gaji", "abc", "x").contains("[!] Jumlah tidak valid!"));
        check("view: keterangan kosong ditolak", run("1", "", "10", "x").contains("[!] Keterangan tidak boleh kosong!"));
        System.exit(failed == 0 ? 0 : 1);
    }
}
