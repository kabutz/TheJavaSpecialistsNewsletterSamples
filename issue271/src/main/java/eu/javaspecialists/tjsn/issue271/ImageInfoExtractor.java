package eu.javaspecialists.tjsn.issue271;

public interface ImageInfoExtractor {
    String getUrl();

    ImageInfo extract(String body);
}
