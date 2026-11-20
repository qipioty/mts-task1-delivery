package citystat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsCalculator {

    private static final float LOAD_FACTOR = 0.75f;

    public AddressStatistics calculate(List<Address> addresses) {
        Map<Address, Integer> occurrences = countOccurrences(addresses);
        Map<Address, Integer> duplicates = new HashMap<>();
        Map<String, FloorCounter> floorsByCity = new HashMap<>();

        for (Map.Entry<Address, Integer> entry : occurrences.entrySet()) {
            Address address = entry.getKey();
            if (entry.getValue() > 1) {
                duplicates.put(address, entry.getValue());
            }
            floorsByCity
                    .computeIfAbsent(address.city(), city -> new FloorCounter())
                    .add(address.floor());
        }
        return new AddressStatistics(duplicates, floorsByCity);
    }

    private Map<Address, Integer> countOccurrences(List<Address> addresses) {
        Map<Address, Integer> occurrences = new HashMap<>((int) (addresses.size() / LOAD_FACTOR) + 1);
        for (Address address : addresses) {
            occurrences.merge(address, 1, Integer::sum);
        }
        return occurrences;
    }
}