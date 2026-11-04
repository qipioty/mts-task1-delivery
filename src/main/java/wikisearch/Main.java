package wikisearch;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        input.readQuery().ifPresent(q -> System.out.println("Вы ввели: " + q));
    }
}