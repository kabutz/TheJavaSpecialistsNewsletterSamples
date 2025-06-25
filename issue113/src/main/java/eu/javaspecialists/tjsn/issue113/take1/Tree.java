package eu.javaspecialists.tjsn.issue113.take1;


public enum Tree implements EnumConverter {
    Acorn(30), Birch(60), Cedar(40);

    private final byte value;

    Tree(int value) {
        this.value = (byte) value;
    }

    public byte convert() {
        return value;
    }
}
