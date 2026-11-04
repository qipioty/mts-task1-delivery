package wikisearch;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        WikipediaClient client = new WikipediaClient();
        ResponseParser parser = new ResponseParser();

        input.readQuery().ifPresent(query -> {
            try {
                List<SearchResult> results = parser.parse(client.search(query));
                for (SearchResult r : results) {
                    System.out.println(r.getPageId() + " | " + r.getTitle() + " | " + r.getSnippet());
                }
                System.out.println("Найдено: " + results.size());
            } catch (WikipediaApiException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        });
    }
}