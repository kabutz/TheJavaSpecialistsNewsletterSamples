package eu.javaspecialists.tjsn.issue269;

import java.util.*;

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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != Person.class) return false;
        return equalsName((Person) o);
    }

    protected boolean equalsName(Person that) {
        return Objects.equals(name, that.name);
    }
}

