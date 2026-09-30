package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ContactUseCase;

public class ContactView {
    private final InputUtil input;
    private final ContactUseCase useCase;
    private final ContactPresenter presenter;

    public ContactView(InputUtil input, ContactUseCase useCase, ContactPresenter presenter) {
        this.input = input;
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showContacts(useCase.getAllContacts());
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
            case "1" -> addContact();
            case "2" -> updateContact();
            case "3" -> searchContact();
            case "4" -> sortContact();
            case "5" -> removeContact();
            default -> presenter.showInvalidChoice();
        }
    }

    private void addContact() {
        presenter.showTitle("Menambah Kontak");
        String name = input.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            return;
        }

        String phone = input.input("Telepon");
        if (InputUtil.isCancel(phone)) {
            return;
        }

        String email = input.input("Email");
        if (InputUtil.isCancel(email)) {
            return;
        }

        presenter.showAddSuccess(useCase.addContact(name, phone, email));
    }

    private void updateContact() {
        presenter.showTitle("Mengubah Kontak");
        String strId = input.input("ID Kontak yang diubah (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String name = blankToNull(input.input("Nama Baru (Kosongkan jika tidak ingin mengubah)"));
        String phone = blankToNull(input.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)"));
        String email = blankToNull(input.input("Email Baru (Kosongkan jika tidak ingin mengubah)"));

        if (useCase.updateContact(id, name, phone, email)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    private void searchContact() {
        presenter.showTitle("Mencari Kontak");
        String keyword = input.input("Kata Kunci (x Jika Batal)");
        if (!InputUtil.isCancel(keyword)) {
            presenter.showSearchResults(useCase.searchContacts(keyword), keyword);
        }
    }

    private void sortContact() {
        presenter.showTitle("Mengurutkan Kontak");
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

        presenter.showSortedContacts(useCase.sortContacts(option));
    }

    private void removeContact() {
        presenter.showTitle("Menghapus Kontak");
        String strId = input.input("[ID Kontak] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (useCase.removeContact(id)) {
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
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            default -> null;
        };
    }
}
