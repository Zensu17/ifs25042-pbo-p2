package adapter.presenter;

import domain.entity.Contact;
import java.io.PrintStream;
import java.util.List;

public class ContactPresenter {
    private final PrintStream out;

    public ContactPresenter(PrintStream out) {
        this.out = out;
    }

    private String format(Contact c) {
        return c.getId() + " | " + c.getName() + " | " + c.getPhone() + " | " + c.getEmail();
    }

    private void printList(List<Contact> list, String header, String emptyMessage) {
        out.println(header);
        if (list.isEmpty()) {
            out.println(emptyMessage);
            return;
        }
        for (Contact c : list) {
            out.println(format(c));
        }
    }

    public void showMenu() {
        out.println("Menu:");
        out.println("1. Tambah");
        out.println("2. Ubah");
        out.println("3. Cari");
        out.println("4. Urutkan");
        out.println("5. Hapus");
        out.println("x. Keluar");
    }

    public void showSortMenu() {
        out.println("Pilihan Pengurutan:");
        out.println("1. Nama (A-Z)");
        out.println("2. Nama (Z-A)");
        out.println("x. Batal");
    }

    public void showTitle(String title) {
        out.println("[" + title + "]");
    }

    public void showBlankLine() {
        out.println();
    }

    public void showError(String message) {
        out.println("[!] " + message);
    }

    public void showContacts(List<Contact> list) {
        printList(list, "Daftar Kontak:", "- Data kontak belum tersedia!");
    }

    public void showSearchResults(List<Contact> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Kontak tidak ditemukan!");
    }

    public void showSortedContacts(List<Contact> list) {
        printList(list, "Daftar Kontak (Terurut):", "- Data kontak belum tersedia!");
    }

    public void showAddSuccess(Contact c) {
        out.println("Berhasil menambah kontak: " + format(c));
    }

    public void showRemoveSuccess() {
        out.println("Berhasil menghapus kontak.");
    }

    public void showRemoveFailed(int id) {
        out.println("[!] Gagal menghapus kontak dengan ID: " + id + ".");
    }

    public void showUpdateSuccess() {
        out.println("Berhasil mengubah kontak.");
    }

    public void showUpdateFailed(int id) {
        out.println("[!] Gagal mengubah kontak dengan ID: " + id + ".");
    }

    public void showInvalidChoice() {
        out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        out.println("[!] ID tidak valid!");
    }

    public void showInvalidSortOption() {
        out.println("[!] Pilihan tidak valid!");
    }
}
