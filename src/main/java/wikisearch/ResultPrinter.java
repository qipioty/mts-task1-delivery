package wikisearch;

import java.util.List;

public class ResultPrinter {
    private static final int MAX_SNIPPET_LENGTH = 150;

    public void print(List<SearchResult> results) {
        if (results.isEmpty()) {
            System.out.println("По вашему запросу ничего не найдено.");
            return;
        }
        System.out.println();
        System.out.println("Результаты поиска:");
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            System.out.println((i + 1) + ". " + result.getTitle());
            System.out.println("   " + shorten(result.getSnippet()));
        }
        System.out.println();
    }

    private String shorten(String text) {
        if (text.length() <= MAX_SNIPPET_LENGTH) {
            return text;
        }
        return text.substring(0, MAX_SNIPPET_LENGTH) + "...";
    }
}