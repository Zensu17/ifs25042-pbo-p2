package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

public class ActivityView {
    private final InputUtil input;
    private final ActivityUseCase useCase;
    private final ActivityPresenter presenter;

    public ActivityView(InputUtil input, ActivityUseCase useCase, ActivityPresenter presenter) {
        this.input = input;
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showActivities(useCase.getAllActivities());
            presenter.showMenu();

            String choice = input.input("Pilih");
            if (InputUtil.isCancel(choice)) {
                return;
            }

            handleChoice(choice);
            presenter.showBlankLine();
        }
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1" -> addActivity();
            case "2" -> updateActivity();
            case "3" -> searchActivity();
            case "4" -> sortActivity();
            case "5" -> removeActivity();
            default -> presenter.showInvalidChoice();
        }
    }

    private void addActivity() {
        presenter.showTitle("Menambah Kegiatan");
        String title = input.input("Judul (x Jika Batal)");
        if (InputUtil.isCancel(title)) {
            return;
        }

        String day = input.input("Hari (x Jika Batal)");
        if (InputUtil.isCancel(day)) {
            return;
        }

        String time = input.input("Waktu (x Jika Batal)");
        if (InputUtil.isCancel(time)) {
            return;
        }

        presenter.showAddSuccess(useCase.addActivity(title, day, time));
    }

    private void updateActivity() {
        presenter.showTitle("Mengubah Kegiatan");
        String strId = input.input("ID Kegiatan yang diubah (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String title = blankToNull(input.input("Judul Baru (Kosongkan jika tidak ingin mengubah)"));
        String day = blankToNull(input.input("Hari Baru (Kosongkan jika tidak ingin mengubah)"));
        String time = blankToNull(input.input("Waktu Baru (Kosongkan jika tidak ingin mengubah)"));

        if (useCase.updateActivity(id, title, day, time)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    private void searchActivity() {
        presenter.showTitle("Mencari Kegiatan");
        String keyword = input.input("Kata Kunci (x Jika Batal)");
        if (!InputUtil.isCancel(keyword)) {
            presenter.showSearchResults(useCase.searchActivities(keyword), keyword);
        }
    }

    private void sortActivity() {
        presenter.showTitle("Mengurutkan Kegiatan");
        presenter.showSortMenu();

        String choice = input.input("Pilih");
        if (InputUtil.isCancel(choice)) {
            return;
        }

        SortOption option = mapSortOption(choice);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedActivities(useCase.sortActivities(option));
    }

    private void removeActivity() {
        presenter.showTitle("Menghapus Kegiatan");
        String strId = input.input("[ID Kegiatan] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (useCase.removeActivity(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** @return ID, atau null jika tidak valid (error sudah ditampilkan) */
    private Integer parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            presenter.showInvalidId();
            return null;
        }
    }

    /** Input kosong (atau EOF) berarti field tidak diubah. */
    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

    private SortOption mapSortOption(String choice) {
        return switch (choice) {
            case "1" -> SortOption.DAY;
            case "2" -> SortOption.TIME;
            case "3" -> SortOption.TITLE_ASC;
            case "4" -> SortOption.TITLE_DESC;
            default -> null;
        };
    }
}
