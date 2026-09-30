package framework.util;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Pembaca input konsol dengan format prompt seragam ("label : ").
 * Scanner dan PrintStream di-inject dari {@code App} sehingga mudah diganti saat pengujian.
 */
public class InputUtil {
    private static final String CANCEL_KEYWORD = "x";

    private final Scanner scanner;
    private final PrintStream out;

    public InputUtil(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * Menampilkan prompt lalu membaca satu baris input.
     *
     * @param label teks prompt sebelum tanda " : "
     * @return baris input yang sudah di-trim, atau {@code null} jika input habis (EOF)
     */
    public String input(String label) {
        out.print(label + " : ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
    }

    /** @return true jika user mengetik "x" (huruf besar/kecil) atau input sudah habis */
    public static boolean isCancel(String value) {
        return value == null || CANCEL_KEYWORD.equalsIgnoreCase(value);
    }
}
