package wikisearch;

public class WikipediaApiException extends Exception {

    public WikipediaApiException(String message) {
        super(message);
    }

    public WikipediaApiException(String message, Throwable cause) {
        super(message, cause);
    }
}