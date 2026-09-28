package adapter.presenter;

import domain.entity.Transaction;

import java.io.PrintStream;
import java.util.List;

public class FinancePresenter {
    private final PrintStream out;

    public FinancePresenter() {
        this(System.out);
    }

    public FinancePresenter(PrintStream out) {
        this.out = out;
    }

    public String format(Transaction transaction) {
        return transaction.getId()
                + " | "
                + transaction.getDescription()
                + " | Rp "
                + transaction.getAmount()
                + " | "
                + transaction.getType().getLabel();
    }

    public void added(Transaction transaction, long balance) {
        out.println("Berhasil menambah transaksi: " + format(transaction));
        out.println();
    }

    public void transactionList(List<Transaction> transactions, long balance) {
        out.println("Daftar Transaksi:");
        if (transactions.isEmpty()) {
            out.println("- Belum ada transaksi!");
        } else {
            printList(transactions);
        }
        printBalance(balance);
    }

    public void searchResult(String keyword, List<Transaction> results, long balance) {
        out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (results.isEmpty()) {
            out.println("- Transaksi tidak ditemukan!");
        } else {
            printList(results);
        }
        out.println();
    }

    public void sortedList(List<Transaction> results, long balance) {
        out.println("Daftar Transaksi (Terurut):");
        printList(results);
        out.println();
    }

    public void deleted() {
        out.println("Berhasil menghapus transaksi.");
        out.println();
    }

    public void currentBalance(long balance) {
        out.println("Saldo saat ini: Rp " + balance);
        out.println();
    }

    public void selectedChoice(String choice) {
        String label;
        switch (choice) {
            case "1":
                label = "Tambah Pemasukan";
                break;
            case "2":
                label = "Tambah Pengeluaran";
                break;
            case "3":
                label = "Cari Transaksi";
                break;
            case "4":
                label = "Urutkan Transaksi";
                break;
            case "5":
                return;
            case "6":
                label = "Hapus Transaksi";
                break;
            default:
                return;
        }
        out.println("[" + label + "]");
    }

    public void invalidChoice() {
        out.println("[!] Pilihan tidak dimengerti.");
        out.println();
    }

    public void invalidSortChoice() {
        out.println("[!] Pilihan tidak valid!");
        out.println();
    }

    public void invalidAmount() {
        out.println("[!] Jumlah tidak valid!");
        out.println();
    }

    public void invalidId() {
        out.println("[!] ID tidak valid!");
        out.println();
    }

    public void deleteFailed(int id) {
        out.println("[!] Gagal menghapus transaksi dengan ID: " + id + ".");
        out.println();
    }

    private void printList(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            out.println(format(transaction));
        }
    }

    private void printBalance(long balance) {
        out.println("Saldo: Rp " + balance);
    }
}
