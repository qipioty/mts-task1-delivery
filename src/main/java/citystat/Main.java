package citystat;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws AddressParseException {
        AddressParser parser = new CsvAddressParser();
        StatisticsCalculator calculator = new StatisticsCalculator();
        StatisticsPrinter printer = new StatisticsPrinter();

        long start = System.currentTimeMillis();
        List<Address> addresses = parser.parse(Path.of("C:\\Users\\User\\Downloads\\address.csv"));
        AddressStatistics statistics = calculator.calculate(addresses);
        long time = System.currentTimeMillis() - start;

        printer.print(statistics, time);
    }
}