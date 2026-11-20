package citystat;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleInput {

    private static final String EXIT_COMMAND = "exit";
    private static final char QUOTE = '"';

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    public Optional<String> readPath() {
        while (true) {
            System.out.print("Введите путь к файлу (.csv или .xml) или exit для выхода: ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }
            String line = stripQuotes(scanner.nextLine().trim());
            if (line.equalsIgnoreCase(EXIT_COMMAND)) {
                return Optional.empty();
            }
            if (line.isEmpty()) {
                System.out.println("Путь не может быть пустым.");
                continue;
            }
            return Optional.of(line);
        }
    }

    private String stripQuotes(String text) {
        if (text.length() >= 2 && text.charAt(0) == QUOTE && text.charAt(text.length() - 1) == QUOTE) {
            return text.substring(1, text.length() - 1).trim();
        }
        return text;
    }
}