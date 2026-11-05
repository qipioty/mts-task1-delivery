package wikisearch;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WikipediaClient {
    private static final String SEARCH_URL =
            "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srsearch=";
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(TIMEOUT)
            .build();

    public String search(String query) throws WikipediaApiException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SEARCH_URL + encodedQuery))
                .timeout(TIMEOUT)
                .header("User-Agent", "WikiSearchConsoleApp/1.0 (educational project)")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new WikipediaApiException(
                        "Сервер вернул код ответа " + response.statusCode());
            }
            return response.body();
        } catch (IOException e) {
            throw new WikipediaApiException("Ошибка сети: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WikipediaApiException("Запрос был прерван", e);
        }
    }
}