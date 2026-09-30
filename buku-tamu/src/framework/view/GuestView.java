package framework.view;

import adapter.presenter.GuestPresenter;
import framework.util.InputUtil;
import usecase.GuestUseCase;

public class GuestView {
    private final InputUtil input;
    private final GuestUseCase useCase;
    private final GuestPresenter presenter;

    public GuestView(InputUtil input, GuestUseCase useCase, GuestPresenter presenter) {
        this.input = input;
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showGuests(useCase.getAllGuests());
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
            case "1" -> registerGuest();
            case "2" -> searchGuest();
            case "3" -> removeGuest();
            default -> presenter.showInvalidChoice();
        }
    }

    private void registerGuest() {
        presenter.showTitle("Mendaftarkan Tamu");
        String name = input.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            return;
        }

        String purpose = input.input("Tujuan Kunjungan (x Jika Batal)");
        if (InputUtil.isCancel(purpose)) {
            return;
        }

        presenter.showRegisterSuccess(useCase.registerGuest(name, purpose));
    }

    private void searchGuest() {
        presenter.showTitle("Mencari Tamu");
        String keyword = input.input("Nama (x Jika Batal)");
        if (!InputUtil.isCancel(keyword)) {
            presenter.showSearchResults(useCase.searchGuests(keyword), keyword);
        }
    }

    private void removeGuest() {
        presenter.showTitle("Menghapus Tamu");
        String strId = input.input("[ID Tamu] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (useCase.removeGuest(id)) {
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
}
