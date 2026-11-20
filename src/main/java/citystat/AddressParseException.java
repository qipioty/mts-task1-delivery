package citystat;

public class AddressParseException extends Exception {

    public AddressParseException(String message) {
        super(message);
    }

    public AddressParseException(String message, Throwable cause) {
        super(message, cause);
    }
}