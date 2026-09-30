package adapter.presenter;

import domain.entity.Activity;
import java.io.PrintStream;
import java.util.List;

public class ActivityPresenter {
    private final PrintStream out;

    public ActivityPresenter(PrintStream out) {
        this.out = out;
    }

    private String format(Activity a) {
        return a.getId() + " | " + a.getTitle() + " | " + a.getDay() + " | " + a.getTime();
    }

    private void printList(List<Activity> list, String header, String emptyMessage) {
        out.println(header);
        if (list.isEmpty()) {
            out.println(emptyMessage);
            return;
        }
        for (Activity a : list) {
            out.println(format(a));
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
        out.println("1. Hari (Senin -> Minggu)");
        out.println("2. Waktu (Awal -> Akhir)");
        out.println("3. Judul (A-Z)");
        out.println("4. Judul (Z-A)");
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

    public void showActivities(List<Activity> list) {
        printList(list, "Daftar Kegiatan:", "- Data kegiatan belum tersedia!");
    }

    public void showSearchResults(List<Activity> list, String keyword) {
        printList(list, "Hasil Pencarian: \"" + keyword + "\"", "- Kegiatan tidak ditemukan!");
    }

    public void showSortedActivities(List<Activity> list) {
        printList(list, "Daftar Kegiatan (Terurut):", "- Data kegiatan belum tersedia!");
    }

    public void showAddSuccess(Activity a) {
        out.println("Berhasil menambah kegiatan: " + format(a));
    }

    public void showRemoveSuccess() {
        out.println("Berhasil menghapus kegiatan.");
    }

    public void showRemoveFailed(int id) {
        out.println("[!] Gagal menghapus kegiatan dengan ID: " + id + ".");
    }

    public void showUpdateSuccess() {
        out.println("Berhasil mengubah kegiatan.");
    }

    public void showUpdateFailed(int id) {
        out.println("[!] Gagal mengubah kegiatan dengan ID: " + id + ".");
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
