import adapter.presenter.ContactPresenter;
import adapter.repository.ContactRepository;
import domain.repository.IContactRepository;
import framework.util.InputUtil;
import framework.view.ContactView;
import java.util.Scanner;
import usecase.ContactUseCase;

/**
 * Titik masuk aplikasi kontak teman (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        IContactRepository contactRepository = new ContactRepository();
        ContactUseCase contactUseCase = new ContactUseCase(contactRepository);
        ContactPresenter contactPresenter = new ContactPresenter(System.out);
        InputUtil inputUtil = new InputUtil(new Scanner(System.in), System.out);
        ContactView contactView = new ContactView(inputUtil, contactUseCase, contactPresenter);

        contactView.show();
    }
}
