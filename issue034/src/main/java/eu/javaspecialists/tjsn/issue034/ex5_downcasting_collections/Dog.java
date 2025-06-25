package eu.javaspecialists.tjsn.issue034.ex5_downcasting_collections;

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
}