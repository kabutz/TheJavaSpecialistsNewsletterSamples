package eu.javaspecialists.tjsn.issue271;

public class Image {
    private final String title;
    private final byte[] image;

    public Image(String title, byte[] image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public byte[] getImage() {
        return image;
    }
}

