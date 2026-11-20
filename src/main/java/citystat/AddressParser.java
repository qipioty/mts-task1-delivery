package citystat;

import java.nio.file.Path;
import java.util.List;

public interface AddressParser {

    List<Address> parse(Path file) throws AddressParseException;
}