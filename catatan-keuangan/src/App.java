import adapter.presenter.FinancePresenter;
import adapter.repository.TransactionRepository;
import domain.repository.ITransactionRepository;
import framework.util.InputUtil;
import framework.view.FinanceView;
import java.util.Scanner;
import usecase.FinanceUseCase;

/**
 * Titik masuk aplikasi catatan keuangan (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        ITransactionRepository transactionRepository = new TransactionRepository();
        FinanceUseCase financeUseCase = new FinanceUseCase(transactionRepository);
        FinancePresenter financePresenter = new FinancePresenter(System.out);
        InputUtil inputUtil = new InputUtil(new Scanner(System.in), System.out);
        FinanceView financeView = new FinanceView(inputUtil, financeUseCase, financePresenter);

        financeView.show();
    }
}
