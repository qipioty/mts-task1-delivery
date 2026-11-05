package wikisearch;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class BrowserOpener {

    public boolean open(String url) {
        if (!Desktop.isDesktopSupported()) {
            return false;
        }
        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
            return false;
        }
        try {
            desktop.browse(URI.create(url));
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }
}