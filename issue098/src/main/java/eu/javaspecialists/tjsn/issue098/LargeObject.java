package eu.javaspecialists.tjsn.issue098;

public class LargeObject {
    private final byte[] space = new byte[1024 * 1024];
    private final int id;

    public LargeObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
