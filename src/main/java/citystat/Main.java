package citystat;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws AddressParseException {
        AddressParser parser = new CsvAddressParser();
        StatisticsCalculator calculator = new StatisticsCalculator();

        long start = System.currentTimeMillis();
        List<Address> addresses = parser.parse(Path.of("C:\\Users\\User\\Downloads\\address.csv"));
        AddressStatistics statistics = calculator.calculate(addresses);
        long time = System.currentTimeMillis() - start;

        long uniqueBuildings = 0;
        for (FloorCounter counter : statistics.floorsByCity().values()) {
            for (int floor = FloorCounter.MIN_FLOOR; floor <= FloorCounter.MAX_FLOOR; floor++) {
                uniqueBuildings += counter.get(floor);
            }
        }
        long extraCopies = 0;
        for (int count : statistics.duplicates().values()) {
            extraCopies += count - 1;
        }

        System.out.println("Записей: " + addresses.size());
        System.out.println("Дублирующихся адресов: " + statistics.duplicates().size());
        System.out.println("Городов: " + statistics.floorsByCity().size());
        System.out.println("Уникальных зданий + лишних копий: " + (uniqueBuildings + extraCopies));
        System.out.println("Время, мс: " + time);
    }
}