package wikisearch;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        WikipediaClient client = new WikipediaClient();
        ResponseParser parser = new ResponseParser();
        ResultPrinter printer = new ResultPrinter();

        input.readQuery().ifPresent(query -> {
            try {
                List<SearchResult> results = parser.parse(client.search(query));
                printer.print(results);
            } catch (WikipediaApiException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        });
    }
}