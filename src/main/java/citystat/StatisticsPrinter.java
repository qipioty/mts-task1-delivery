package citystat;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class StatisticsPrinter {

    private static final int CITY_COLUMN_WIDTH = 30;
    private static final int FLOOR_COLUMN_WIDTH = 8;

    public void print(AddressStatistics statistics, long processingTimeMillis) {
        printDuplicates(statistics.duplicates());
        printFloors(statistics.floorsByCity());
        printTime(processingTimeMillis);
    }

    private void printDuplicates(Map<Address, Integer> duplicates) {
        System.out.println();
        System.out.println("Дублирующиеся записи:");
        if (duplicates.isEmpty()) {
            System.out.println("  не найдены");
            return;
        }
        duplicates.entrySet().stream()
                .sorted(Map.Entry.<Address, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf(
                        "  %s, %s, %d, %d - повторов: %d%n",
                        entry.getKey().city(),
                        entry.getKey().street(),
                        entry.getKey().house(),
                        entry.getKey().floor(),
                        entry.getValue()));
    }

    private void printFloors(Map<String, FloorCounter> floorsByCity) {
        System.out.println();
        System.out.println("Количество зданий по этажности:");
        System.out.printf("  %-" + CITY_COLUMN_WIDTH + "s", "Город");
        for (int floor = FloorCounter.MIN_FLOOR; floor <= FloorCounter.MAX_FLOOR; floor++) {
            System.out.printf("%" + FLOOR_COLUMN_WIDTH + "s", floor + " эт.");
        }
        System.out.println();
        new TreeMap<>(floorsByCity).forEach(this::printCityRow);
    }

    private void printCityRow(String city, FloorCounter counter) {
        System.out.printf("  %-" + CITY_COLUMN_WIDTH + "s", city);
        for (int floor = FloorCounter.MIN_FLOOR; floor <= FloorCounter.MAX_FLOOR; floor++) {
            System.out.printf("%" + FLOOR_COLUMN_WIDTH + "d", counter.get(floor));
        }
        System.out.println();
    }

    private void printTime(long processingTimeMillis) {
        System.out.println();
        System.out.printf(
                "Время обработки файла: %d мс (%.2f с)%n",
                processingTimeMillis,
                processingTimeMillis / 1000.0);
    }
}