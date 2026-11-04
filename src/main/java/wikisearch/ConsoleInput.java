package wikisearch;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleInput {
    private static final String EXIT_COMMAND = "exit";

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    public Optional<String> readQuery() {
        while (true) {
            System.out.print("Введите поисковый запрос (exit - выход): ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase(EXIT_COMMAND)) {
                return Optional.empty();
            }
            if (line.isEmpty()) {
                System.out.println("Запрос не может быть пустым.");
                continue;
            }
            return Optional.of(line);
        }
    }
}