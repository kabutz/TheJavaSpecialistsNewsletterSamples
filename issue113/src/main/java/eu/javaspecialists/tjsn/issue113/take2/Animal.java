package eu.javaspecialists.tjsn.issue113.take2;

public enum Animal implements EnumConverter<Animal> {
    Ape(100), Bee(50), Cat(80);

    private static ReverseEnumMap<Animal> map =
            new ReverseEnumMap<Animal>(Animal.class);

    private final byte value;

    Animal(int value) {
        this.value = (byte) value;
    }

    public byte convert() {
        return value;
    }

    public Animal convert(byte val) {
        return map.get(val);
    }
}
