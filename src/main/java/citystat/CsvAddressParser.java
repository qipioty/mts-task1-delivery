package citystat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvAddressParser implements AddressParser {

    private static final char SEPARATOR = ';';
    private static final char QUOTE = '"';
    private static final int COLUMNS_COUNT = 4;

    @Override
    public List<Address> parse(Path file) throws AddressParseException {
        List<Address> addresses = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line).ifPresent(addresses::add);
            }
        } catch (IOException e) {
            throw new AddressParseException("Не удалось прочитать CSV-файл: " + e.getMessage(), e);
        }
        return addresses;
    }

    private Optional<Address> parseLine(String line) {
        List<String> columns = splitLine(line);
        if (columns.size() != COLUMNS_COUNT) {
            return Optional.empty();
        }
        try {
            return Optional.of(new Address(
                    columns.get(0),
                    columns.get(1),
                    Integer.parseInt(columns.get(2)),
                    Integer.parseInt(columns.get(3))));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private List<String> splitLine(String line) {
        List<String> columns = new ArrayList<>(COLUMNS_COUNT);
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            if (symbol == QUOTE) {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == QUOTE) {
                    current.append(QUOTE);
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (symbol == SEPARATOR && !inQuotes) {
                columns.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(symbol);
            }
        }
        columns.add(current.toString().trim());
        return columns;
    }
}