package eu.javaspecialists.tjsn.issue271;

import java.time.*;
import java.time.format.*;

public class DilbertImageInfoExtractor implements ImageInfoExtractor {
    private final LocalDate date;

    public DilbertImageInfoExtractor(LocalDate date) {
        this.date = date;
    }

    public String getUrl() {
        return "https://dilbert.com/strip/" +
                DateTimeFormatter.ISO_DATE.format(date);
    }

    public ImageInfo extract(String body) {
        var title = findProperty(body, "title");
        var imagePath = findProperty(body, "image");
        return new ImageInfo(title, imagePath);
    }

    private static String findProperty(String body,
                                       String filter) {
        String search = "meta name=\"twitter:" + filter +
                "\" content=\"";
        return body.lines().filter(line -> line.contains(search))
                .findFirst()
                .map(line -> line.replaceAll(".*" + search, ""))
                .map(line -> line.replaceAll("\".*", ""))
                .orElseThrow(() -> new IllegalStateException(
                        "Could not find \"" + filter + "\""));
    }
}
