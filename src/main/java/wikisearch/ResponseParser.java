package wikisearch;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

public class ResponseParser {
    private final Gson gson = new Gson();

    public List<SearchResult> parse(String json) throws WikipediaApiException {
        ApiResponse response;
        try {
            response = gson.fromJson(json, ApiResponse.class);
        } catch (JsonSyntaxException e) {
            throw new WikipediaApiException("Не удалось разобрать ответ сервера", e);
        }

        List<SearchResult> results = new ArrayList<>();
        if (response == null || response.query == null || response.query.search == null) {
            return results;
        }
        for (ApiItem item : response.query.search) {
            results.add(new SearchResult(item.title, item.pageid, cleanSnippet(item.snippet)));
        }
        return results;
    }

    private String cleanSnippet(String snippet) {
        if (snippet == null) {
            return "";
        }
        return snippet
                .replaceAll("<[^>]+>", "")
                .replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&")
                .trim();
    }

    private static class ApiResponse {
        private ApiQuery query;
    }

    private static class ApiQuery {
        private List<ApiItem> search;
    }

    private static class ApiItem {
        private String title;
        private int pageid;
        private String snippet;
    }
}