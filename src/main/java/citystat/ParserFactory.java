package citystat;

import java.nio.file.Path;
import java.util.Locale;

public class ParserFactory {

    private static final String CSV_EXTENSION = ".csv";
    private static final String XML_EXTENSION = ".xml";

    public AddressParser create(Path file) throws AddressParseException {
        String name = file.getFileName().toString().toLowerCase(Locale.ROOT);
        if (name.endsWith(CSV_EXTENSION)) {
            return new CsvAddressParser();
        }
        if (name.endsWith(XML_EXTENSION)) {
            return new XmlAddressParser();
        }
        throw new AddressParseException("Неподдерживаемый формат файла. Допустимы .csv и .xml");
    }
}