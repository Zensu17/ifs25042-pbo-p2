import adapter.presenter.GuestPresenter;
import adapter.repository.GuestRepository;
import domain.repository.IGuestRepository;
import framework.util.InputUtil;
import framework.view.GuestView;
import java.util.Scanner;
import usecase.GuestUseCase;

/**
 * Titik masuk aplikasi buku tamu (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        IGuestRepository guestRepository = new GuestRepository();
        GuestUseCase guestUseCase = new GuestUseCase(guestRepository);
        GuestPresenter guestPresenter = new GuestPresenter(System.out);
        InputUtil inputUtil = new InputUtil(new Scanner(System.in), System.out);
        GuestView guestView = new GuestView(inputUtil, guestUseCase, guestPresenter);

        guestView.show();
    }
}
