package wikisearch;

public class SearchResult {
    private final String title;
    private final int pageId;
    private final String snippet;

    public SearchResult(String title, int pageId, String snippet) {
        this.title = title;
        this.pageId = pageId;
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public int getPageId() {
        return pageId;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getUrl() {
        return "https://ru.wikipedia.org/w/index.php?curid=" + pageId;
    }
}