package wikisearch;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        WikipediaClient client = new WikipediaClient();

        input.readQuery().ifPresent(query -> {
            try {
                System.out.println(client.search(query));
            } catch (WikipediaApiException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        });
    }
}