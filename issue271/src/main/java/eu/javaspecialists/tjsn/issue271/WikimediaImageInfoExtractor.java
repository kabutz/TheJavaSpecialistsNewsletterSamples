package eu.javaspecialists.tjsn.issue271;

import java.time.*;
import java.time.format.*;
import java.util.regex.*;

public class WikimediaImageInfoExtractor implements ImageInfoExtractor {
    private final LocalDate date;

    public WikimediaImageInfoExtractor(LocalDate date) {
        this.date = date;
    }

    public String getUrl() {
        return "https://commons.wikimedia.org/wiki/" +
                "Special:FeedItem/potd/" +
                DateTimeFormatter.BASIC_ISO_DATE.format(date) +
                "000000/en";
    }

    public ImageInfo extract(String body) {
        Pattern pattern = Pattern.compile(
                "<img\\s+alt=\"[^\"]+\"\\s+src=\"(?<src>[^\\\"]+)\"");
        Matcher matcher = pattern.matcher(body);
        var title = DateTimeFormatter.BASIC_ISO_DATE.format(date);
        var imagePath = matcher.find() ? matcher.group("src") : null;
        return new ImageInfo(title, imagePath);
    }
}
