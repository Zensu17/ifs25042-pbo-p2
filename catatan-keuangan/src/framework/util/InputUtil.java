package framework.util;

import java.util.Scanner;

public class InputUtil {
    private final Scanner scanner;

    public InputUtil() {
        this(new Scanner(System.in));
    }

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }
}
