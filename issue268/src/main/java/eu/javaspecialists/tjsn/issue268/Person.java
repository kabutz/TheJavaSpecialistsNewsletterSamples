package eu.javaspecialists.tjsn.issue268;

public class Person implements Comparable<Person> {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public int compareTo(Person that) {
        return name.compareTo(that.name);
    }

    public String toString() {
        return name;
    }
}
