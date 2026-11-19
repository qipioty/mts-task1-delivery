package citystat;

import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws AddressParseException {
        AddressParser parser = new XmlAddressParser();
        long start = System.currentTimeMillis();
        List<Address> addresses = parser.parse(Path.of("C:\\Users\\User\\Downloads\\address.xml"));
        long time = System.currentTimeMillis() - start;
        System.out.println("Записей: " + addresses.size());
        System.out.println(addresses.get(0));
        System.out.println("Время, мс: " + time);
    }
}