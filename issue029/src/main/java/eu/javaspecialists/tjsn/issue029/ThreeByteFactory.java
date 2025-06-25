package eu.javaspecialists.tjsn.issue029;

public class ThreeByteFactory implements ObjectFactory {
    private static class ThreeBytes {
        byte b0, b1, b2;
    }

    public Object makeObject() {
        return new ThreeBytes();
    }
}