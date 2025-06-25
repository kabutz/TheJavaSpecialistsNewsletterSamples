package eu.javaspecialists.tjsn.issue109;

public class PersonWithStrategy
        implements ValueBasedEqualityStrategy.ValueSupplier {
    private final String name;
    private final String address;
    private final int age;
    private final EqualityStrategy strategy;

    public PersonWithStrategy(String name, String address,
                              int age, EqualityStrategy strategy) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.strategy = strategy;
        this.strategy.setOwner(this);
    }

    /**
     * This is an example of a constructor that uses a
     * NullEqualityStrategy as default.
     */
    public PersonWithStrategy(String name, String address, int age) {
        this(name, address, age, new NullEqualityStrategy());
    }

    public int hashCode() {
        return strategy.hashCode();
    }

    public boolean equals(Object obj) {
        return strategy.equals(obj);
    }

    public Object[] getValues() {
        return new Object[]{name, address, age};
    }
}
