package wikisearch;

import java.util.List;
import java.util.Optional;

public class WikiSearchApp {
    private final ConsoleInput input = new ConsoleInput();
    private final WikipediaClient client = new WikipediaClient();
    private final ResponseParser parser = new ResponseParser();
    private final ResultPrinter printer = new ResultPrinter();
    private final BrowserOpener browserOpener = new BrowserOpener();

    public void run() {
        while (true) {
            Optional<String> query = input.readQuery();
            if (query.isEmpty()) {
                return;
            }

            List<SearchResult> results;
            try {
                results = parser.parse(client.search(query.get()));
            } catch (WikipediaApiException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            }

            printer.print(results);
            if (results.isEmpty()) {
                continue;
            }
            if (!chooseAndOpen(results)) {
                return;
            }
        }
    }

    private boolean chooseAndOpen(List<SearchResult> results) {
        Optional<Integer> choice = input.readChoice(results.size());
        if (choice.isEmpty()) {
            return false;
        }
        if (choice.get() == 0) {
            return true;
        }
        String url = results.get(choice.get() - 1).getUrl();
        if (!browserOpener.open(url)) {
            System.out.println("Не удалось открыть браузер. Откройте ссылку вручную: " + url);
        }
        return true;
    }
}