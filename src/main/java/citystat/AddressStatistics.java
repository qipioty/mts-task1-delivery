package citystat;

import java.util.Map;

public record AddressStatistics(
        Map<Address, Integer> duplicates,
        Map<String, FloorCounter> floorsByCity) {
}