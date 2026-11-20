package citystat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class XmlAddressParser implements AddressParser {

    private static final String ITEM_TAG = "item";
    private static final String CITY_ATTRIBUTE = "city";
    private static final String STREET_ATTRIBUTE = "street";
    private static final String HOUSE_ATTRIBUTE = "house";
    private static final String FLOOR_ATTRIBUTE = "floor";

    private final XMLInputFactory factory = createFactory();

    @Override
    public List<Address> parse(Path file) throws AddressParseException {
        List<Address> addresses = new ArrayList<>();
        try (InputStream stream = Files.newInputStream(file)) {
            XMLStreamReader reader = factory.createXMLStreamReader(stream);
            try {
                while (reader.hasNext()) {
                    if (reader.next() == XMLStreamConstants.START_ELEMENT
                            && ITEM_TAG.equals(reader.getLocalName())) {
                        readItem(reader).ifPresent(addresses::add);
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException | XMLStreamException e) {
            throw new AddressParseException("Не удалось прочитать XML-файл: " + e.getMessage(), e);
        }
        return addresses;
    }

    private Optional<Address> readItem(XMLStreamReader reader) {
        String city = reader.getAttributeValue(null, CITY_ATTRIBUTE);
        String street = reader.getAttributeValue(null, STREET_ATTRIBUTE);
        String house = reader.getAttributeValue(null, HOUSE_ATTRIBUTE);
        String floor = reader.getAttributeValue(null, FLOOR_ATTRIBUTE);
        if (city == null || street == null || house == null || floor == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(new Address(
                    city.trim(),
                    street.trim(),
                    Integer.parseInt(house.trim()),
                    Integer.parseInt(floor.trim())));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static XMLInputFactory createFactory() {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        inputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        inputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
        return inputFactory;
    }
}