package adapter.presenter;

import domain.entity.Item;
import java.io.PrintStream;
import java.util.List;

public class ItemPresenter {
    private final PrintStream out;

    public ItemPresenter(PrintStream out) {
        this.out = out;
    }

    private String format(Item i) {
        return i.getId() + " | " + i.getName() + " | " + i.getQuantity() + " | " + i.getCategory();
    }

    private void printList(List<Item> list, String header, String emptyMessage) {
        out.println(header);
        if (list.isEmpty()) {
            out.println(emptyMessage);
            return;
        }
        for (Item i : list) {
            out.println(format(i));
        }
    }

    public void showMenu() {
        out.println("Menu:");
        out.println("1. Tambah");
        out.println("2. Ubah Stok");
        out.println("3. Cari");
        out.println("4. Urutkan");
        out.println("5. Hapus");
        out.println("x. Keluar");
    }

    public void showSortMenu() {
        out.println("Pilihan Pengurutan:");
        out.println("1. Nama (A-Z)");
        out.println("2. Nama (Z-A)");
        out.println("3. Jumlah (Terkecil -> Terbesar)");
        out.println("4. Jumlah (Terbesar -> Terkecil)");
        out.println("x. Batal");
    }

    public void showTitle(String title) {
        out.println("[" + title + "]");
    }

    public void showBlankLine() {
        out.println();
    }

    public void showItems(List<Item> list) {
        printList(list, "Daftar Barang:", "- Data barang belum tersedia!");
    }

    public void showSearchResults(List<Item> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Barang tidak ditemukan!");
    }

    public void showSortedItems(List<Item> list) {
        printList(list, "Daftar Barang (Terurut):", "- Data barang belum tersedia!");
    }

    public void showAddSuccess(Item i) {
        out.println("Berhasil menambah barang: " + format(i));
    }

    public void showRemoveSuccess() {
        out.println("Berhasil menghapus barang.");
    }

    public void showRemoveFailed(int id) {
        out.println("[!] Gagal menghapus barang dengan ID: " + id + ".");
    }

    public void showUpdateSuccess() {
        out.println("Berhasil mengubah stok barang.");
    }

    public void showUpdateFailed(int id) {
        out.println("[!] Gagal mengubah stok barang dengan ID: " + id + ".");
    }

    public void showInvalidChoice() {
        out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        out.println("[!] ID tidak valid!");
    }

    public void showInvalidQuantity() {
        out.println("[!] Jumlah stok tidak valid!");
    }

    public void showInvalidSortOption() {
        out.println("[!] Pilihan tidak valid!");
    }
}
