package adapter.presenter;

import domain.entity.Activity;
import java.util.List;

public class ActivityPresenter {

    private String format(Activity a) {
        return String.format("%d | %s | %s | %s", a.getId(), a.getTitle(), a.getDay(), a.getTime());
    }

    private void printList(List<Activity> list, String header, String emptyMessage) {
        System.out.println(header);
        if (list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        for (Activity a : list) {
            System.out.println(format(a));
        }
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
        System.out.printf("Berhasil menambah kegiatan: %s%n", format(a));
    }

    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus kegiatan.");
    }

    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus kegiatan dengan ID: %d.%n", id);
    }

    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah kegiatan.");
    }

    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah kegiatan dengan ID: %d.%n", id);
    }

    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }

    public void showInvalidSortOption() {
        System.out.println("[!] Pilihan tidak valid!");
    }
}
