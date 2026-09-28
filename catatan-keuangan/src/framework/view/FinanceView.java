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

    public FinanceView(FinanceUseCase useCase, FinancePresenter presenter) {
        this(new InputUtil(), useCase, presenter);
    }

    public FinanceView(InputUtil input, FinanceUseCase useCase, FinancePresenter presenter) {
        this.input = input;
        this.useCase = useCase;
        this.presenter = presenter;
    }

    public void show() {
        run();
    }

    public void run() {
        while (true) {
            presenter.transactionList(useCase.getAll(), useCase.getBalance());
            printMenu();
            String choice = input.readLine();
            if (choice == null || choice.equalsIgnoreCase("x")) {
                return;
            }

            presenter.selectedChoice(choice);
            switch (choice) {
                case "1":
                    addTransaction(TransactionType.INCOME);
                    break;
                case "2":
                    addTransaction(TransactionType.EXPENSE);
                    break;
                case "3":
                    search();
                    break;
                case "4":
                    sort();
                    break;
                case "5":
                    presenter.currentBalance(useCase.getBalance());
                    break;
                case "6":
                    delete();
                    break;
                default:
                    presenter.invalidChoice();
                    break;
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
        System.out.print("Pilih : ");
    }

    private void addTransaction(TransactionType type) {
        System.out.print("Keterangan (x Jika Batal) : ");
        String description = input.readLine();
        if (description == null || description.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        System.out.print("Jumlah : ");
        Long amount = parsePositiveAmount(input.readLine());
        if (amount == null) {
            presenter.invalidAmount();
            return;
        }

        presenter.added(useCase.add(description, amount, type), useCase.getBalance());
    }

    private void search() {
        System.out.print("Kata Kunci (x Jika Batal) : ");
        String keyword = input.readLine();
        if (keyword == null || keyword.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        presenter.searchResult(keyword, useCase.search(keyword), useCase.getBalance());
    }

    private void sort() {
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");
        System.out.print("Pilih : ");
        String choice = input.readLine();
        if (choice == null) {
            return;
        }
        if (choice.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        SortOption option = SortOption.fromChoice(choice);
        if (option == null) {
            presenter.invalidSortChoice();
            return;
        }

        presenter.sortedList(useCase.sort(option), useCase.getBalance());
    }

    private void delete() {
        System.out.print("ID Transaksi (x Jika Batal) : ");
        String raw = input.readLine();
        if (raw == null || raw.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }
        Integer id = parseId(raw);
        if (id == null) {
            presenter.invalidId();
            return;
        }

        if (!useCase.delete(id)) {
            presenter.deleteFailed(id);
            return;
        }

        presenter.deleted();
    }

    private Long parsePositiveAmount(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            long amount = Long.parseLong(value);
            return amount > 0 ? amount : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private Integer parseId(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
