package adapter.presenter;

import domain.entity.Transaction;
import java.io.PrintStream;
import java.util.List;

public class FinancePresenter {
    private final PrintStream out;

    public FinancePresenter(PrintStream out) {
        this.out = out;
    }

    private String format(Transaction t) {
        return t.getId() + " | " + t.getDescription() + " | Rp " + t.getAmount() + " | " + t.getType().getLabel();
    }

    private void printItems(List<Transaction> list) {
        for (Transaction t : list) {
            out.println(format(t));
        }
    }

    private void printList(List<Transaction> list, String header, String emptyMessage) {
        out.println(header);
        if (list.isEmpty()) {
            out.println(emptyMessage);
            return;
        }
        printItems(list);
    }

    public void showMenu() {
        out.println("Menu:");
        out.println("1. Tambah Pemasukan");
        out.println("2. Tambah Pengeluaran");
        out.println("3. Cari");
        out.println("4. Urutkan");
        out.println("5. Lihat Saldo");
        out.println("6. Hapus");
        out.println("x. Keluar");
    }

    public void showSortMenu() {
        out.println("1. Jumlah (Terkecil)");
        out.println("2. Jumlah (Terbesar)");
        out.println("3. Pemasukan Dulu");
        out.println("4. Pengeluaran Dulu");
        out.println("x. Batal");
    }

    public void showTitle(String title) {
        out.println("[" + title + "]");
    }

    public void showBlankLine() {
        out.println();
    }

    public void showTransactions(List<Transaction> list, long balance) {
        printList(list, "Daftar Transaksi:", "- Belum ada transaksi!");
        out.println("Saldo: Rp " + balance);
    }

    public void showSearchResults(List<Transaction> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Transaksi tidak ditemukan!");
    }

    public void showSortedTransactions(List<Transaction> list) {
        out.println("Daftar Transaksi (Terurut):");
        printItems(list);
    }

    public void showBalance(long balance) {
        out.println("Saldo saat ini: Rp " + balance);
    }

    public void showAddSuccess(Transaction t) {
        out.println("Berhasil menambah transaksi: " + format(t));
    }

    public void showRemoveSuccess() {
        out.println("Berhasil menghapus transaksi.");
    }

    public void showRemoveFailed(int id) {
        out.println("[!] Gagal menghapus transaksi dengan ID: " + id + ".");
    }

    public void showInvalidChoice() {
        out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        out.println("[!] ID tidak valid!");
    }

    public void showInvalidAmount() {
        out.println("[!] Jumlah tidak valid!");
    }

    public void showInvalidSortOption() {
        out.println("[!] Pilihan tidak valid!");
    }
}
