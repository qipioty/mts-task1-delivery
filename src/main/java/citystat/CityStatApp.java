package citystat;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CityStatApp {

    private static final long NANOS_IN_MILLI = 1_000_000L;

    private final ConsoleInput consoleInput = new ConsoleInput();
    private final ParserFactory parserFactory = new ParserFactory();
    private final StatisticsCalculator calculator = new StatisticsCalculator();
    private final StatisticsPrinter printer = new StatisticsPrinter();

    public void run() {
        while (true) {
            Optional<String> input = consoleInput.readPath();
            if (input.isEmpty()) {
                return;
            }
            process(input.get());
        }
    }

    private void process(String pathText) {
        try {
            Path file = Path.of(pathText);
            if (!Files.isRegularFile(file)) {
                System.out.println("Файл не найден: " + pathText);
                return;
            }
            AddressParser parser = parserFactory.create(file);

            long start = System.nanoTime();
            List<Address> addresses = parser.parse(file);
            AddressStatistics statistics = calculator.calculate(addresses);
            long timeMillis = (System.nanoTime() - start) / NANOS_IN_MILLI;

            printer.print(statistics, timeMillis);
            System.out.println();
        } catch (InvalidPathException e) {
            System.out.println("Некорректный путь: " + pathText);
        } catch (AddressParseException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Непредвиденная ошибка при обработке файла: " + e.getMessage());
        }
    }
}