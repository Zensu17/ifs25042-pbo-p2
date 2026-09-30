package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.TransactionType;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

public class FinanceView {
    private final InputUtil input;
    private final FinanceUseCase useCase;
    private final FinancePresenter presenter;

    public FinanceView(InputUtil input, FinanceUseCase useCase, FinancePresenter presenter) {
        this.input = input;
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        while (true) {
            presenter.showTransactions(useCase.getAllTransactions(), useCase.getBalance());
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
            case "1" -> addTransaction(TransactionType.INCOME);
            case "2" -> addTransaction(TransactionType.EXPENSE);
            case "3" -> searchTransaction();
            case "4" -> sortTransaction();
            case "5" -> presenter.showBalance(useCase.getBalance());
            case "6" -> removeTransaction();
            default -> presenter.showInvalidChoice();
        }
    }

    private void addTransaction(TransactionType type) {
        presenter.showTitle("Tambah " + type.getLabel());
        String description = input.input("Keterangan (x Jika Batal)");
        if (InputUtil.isCancel(description)) {
            return;
        }

        Long amount = parsePositiveAmount(input.input("Jumlah"));
        if (amount == null) {
            presenter.showInvalidAmount();
            return;
        }

        presenter.showAddSuccess(useCase.addTransaction(description, amount, type));
    }

    private void searchTransaction() {
        presenter.showTitle("Cari Transaksi");
        String keyword = input.input("Kata Kunci (x Jika Batal)");
        if (!InputUtil.isCancel(keyword)) {
            presenter.showSearchResults(useCase.searchTransactions(keyword), keyword);
        }
    }

    private void sortTransaction() {
        presenter.showTitle("Urutkan Transaksi");
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

        presenter.showSortedTransactions(useCase.sortTransactions(option));
    }

    private void removeTransaction() {
        presenter.showTitle("Hapus Transaksi");
        String strId = input.input("ID Transaksi (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (useCase.removeTransaction(id)) {
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

    /** @return jumlah > 0, atau null jika bukan angka atau <= 0 */
    private Long parsePositiveAmount(String value) {
        try {
            long amount = Long.parseLong(value);
            return amount > 0 ? amount : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private SortOption mapSortOption(String choice) {
        return switch (choice) {
            case "1" -> SortOption.AMOUNT_ASC;
            case "2" -> SortOption.AMOUNT_DESC;
            case "3" -> SortOption.INCOME_FIRST;
            case "4" -> SortOption.EXPENSE_FIRST;
            default -> null;
        };
    }
}
