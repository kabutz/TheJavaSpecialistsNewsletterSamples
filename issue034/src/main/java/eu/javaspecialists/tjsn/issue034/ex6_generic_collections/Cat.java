package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

public class Cat {
    private final String species;

    public Cat(String species) {
        this.species = species;
    }

    public String toString() {
        return species;
    }

    public void meow() {
        System.out.println(this + ": Meow");
    }

    public static interface Collection extends java.util.Collection {
        Iterator catIterator();
    }

    public static interface Iterator extends java.util.Iterator {
        Cat nextCat();
    }
}