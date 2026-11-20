package citystat;

public class FloorCounter {

    public static final int MIN_FLOOR = 1;
    public static final int MAX_FLOOR = 5;

    private final int[] counts = new int[MAX_FLOOR];

    public void add(int floor) {
        if (floor >= MIN_FLOOR && floor <= MAX_FLOOR) {
            counts[floor - MIN_FLOOR]++;
        }
    }

    public int get(int floor) {
        return counts[floor - MIN_FLOOR];
    }
}