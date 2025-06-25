package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

public class Dog {
    private final String species;

    public Dog(String species) {
        this.species = species;
    }

    public String toString() {
        return species;
    }

    public void bark() {
        System.out.println(this + ": Woof");
    }

    public static interface Collection extends java.util.Collection {
        Iterator dogIterator();
    }

    public static interface Iterator extends java.util.Iterator {
        Dog nextDog();
    }
}