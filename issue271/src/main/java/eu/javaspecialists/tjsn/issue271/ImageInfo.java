package eu.javaspecialists.tjsn.issue271;

public class ImageInfo {
    private final String title;
    private final String imagePath;

    public ImageInfo(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Image create(byte[] data) {
        return new Image(title, data);
    }
}
