package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ItemUseCase;

public class ItemView {
    private final InputUtil input;
    private final ItemUseCase useCase;
    private final ItemPresenter presenter;

    public ItemView(InputUtil input, ItemUseCase useCase, ItemPresenter presenter) {
        this.input = input;
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showItems(useCase.getAllItems());
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
            case "1" -> addItem();
            case "2" -> updateStock();
            case "3" -> searchItem();
            case "4" -> sortItem();
            case "5" -> removeItem();
            default -> presenter.showInvalidChoice();
        }
    }

    private void addItem() {
        presenter.showTitle("Menambah Barang");
        String name = input.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            return;
        }

        String strQuantity = input.input("Jumlah");
        if (InputUtil.isCancel(strQuantity)) {
            return;
        }

        Integer quantity = parseQuantity(strQuantity);
        if (quantity == null) {
            presenter.showInvalidQuantity();
            return;
        }

        String category = input.input("Kategori (x Jika Batal)");
        if (InputUtil.isCancel(category)) {
            return;
        }

        presenter.showAddSuccess(useCase.addItem(name, quantity, category));
    }

    private void updateStock() {
        presenter.showTitle("Mengubah Stok");
        String strId = input.input("ID Barang yang diubah (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String strQuantity = blankToNull(input.input("Jumlah Baru (Kosongkan jika tidak ingin mengubah)"));
        Integer quantity = null; // null berarti stok tidak diubah
        if (strQuantity != null) {
            quantity = parseQuantity(strQuantity);
            if (quantity == null) {
                presenter.showInvalidQuantity();
                return;
            }
        }

        if (useCase.updateStock(id, quantity)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    private void searchItem() {
        presenter.showTitle("Mencari Barang");
        String keyword = input.input("Kata Kunci (x Jika Batal)");
        if (!InputUtil.isCancel(keyword)) {
            presenter.showSearchResults(useCase.searchItems(keyword), keyword);
        }
    }

    private void sortItem() {
        presenter.showTitle("Mengurutkan Barang");
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

        presenter.showSortedItems(useCase.sortItems(option));
    }

    private void removeItem() {
        presenter.showTitle("Menghapus Barang");
        String strId = input.input("[ID Barang] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (useCase.removeItem(id)) {
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

    /** @return stok > 0, atau null jika bukan angka atau <= 0 */
    private Integer parseQuantity(String value) {
        try {
            int quantity = Integer.parseInt(value);
            return quantity > 0 ? quantity : null;
        } catch (NumberFormatException e) {
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
            case "3" -> SortOption.QUANTITY_ASC;
            case "4" -> SortOption.QUANTITY_DESC;
            default -> null;
        };
    }
}
