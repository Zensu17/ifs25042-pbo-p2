package adapter.presenter;

import domain.entity.Guest;
import java.io.PrintStream;
import java.util.List;

public class GuestPresenter {
    private final PrintStream out;

    public GuestPresenter(PrintStream out) {
        this.out = out;
    }

    private String format(Guest g) {
        return g.getId() + " | " + g.getName() + " | " + g.getPurpose();
    }

    private void printList(List<Guest> list, String header, String emptyMessage) {
        out.println(header);
        if (list.isEmpty()) {
            out.println(emptyMessage);
            return;
        }
        for (Guest g : list) {
            out.println(format(g));
        }
    }

    public void showMenu() {
        out.println("Menu:");
        out.println("1. Daftarkan");
        out.println("2. Cari");
        out.println("3. Hapus");
        out.println("x. Keluar");
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

    public void showGuests(List<Guest> list) {
        printList(list, "Daftar Tamu:", "- Data tamu belum tersedia!");
    }

    public void showSearchResults(List<Guest> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Tamu tidak ditemukan!");
    }

    public void showRegisterSuccess(Guest g) {
        out.println("Berhasil mendaftarkan tamu: " + format(g));
    }

    public void showRemoveSuccess() {
        out.println("Berhasil menghapus tamu.");
    }

    public void showRemoveFailed(int id) {
        out.println("[!] Gagal menghapus tamu dengan ID: " + id + ".");
    }

    public void showInvalidChoice() {
        out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        out.println("[!] ID tidak valid!");
    }
}
