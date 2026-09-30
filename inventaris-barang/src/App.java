import adapter.presenter.ItemPresenter;
import adapter.repository.ItemRepository;
import domain.repository.IItemRepository;
import framework.util.InputUtil;
import framework.view.ItemView;
import java.util.Scanner;
import usecase.ItemUseCase;

/**
 * Titik masuk aplikasi inventaris barang (Composition Root).
 * Semua dependency antar layer disusun di sini — satu-satunya tempat
 * yang mengetahui implementasi konkret dari setiap interface.
 */
public class App {
    public static void main(String[] args) {
        IItemRepository itemRepository = new ItemRepository();
        ItemUseCase itemUseCase = new ItemUseCase(itemRepository);
        ItemPresenter itemPresenter = new ItemPresenter(System.out);
        InputUtil inputUtil = new InputUtil(new Scanner(System.in), System.out);
        ItemView itemView = new ItemView(inputUtil, itemUseCase, itemPresenter);

        itemView.show();
    }
}
