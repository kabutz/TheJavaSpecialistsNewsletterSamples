package eu.javaspecialists.tjsn.issue034.ex5_downcasting_collections;

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
}