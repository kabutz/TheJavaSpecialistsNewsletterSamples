package eu.javaspecialists.tjsn.issue113.take1;

public enum Animal implements EnumConverter {
    Ape(100), Bee(50), Cat(80);

    private final byte value;

    Animal(int value) {
        this.value = (byte) value;
    }

    public byte convert() {
        return value;
    }
}

