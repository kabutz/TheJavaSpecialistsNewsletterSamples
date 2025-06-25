package eu.javaspecialists.tjsn.issue225.take1;

import java.io.*;

public final class Person implements Serializable {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        if (this.age < 0)
            throw new IllegalArgumentException("age negative");
        if (this.age > 150)
            throw new IllegalArgumentException("age more than 150");
        if (this.firstName == null || this.firstName.isEmpty())
            throw new IllegalArgumentException("firstName empty");
        if (this.lastName == null || this.lastName.isEmpty())
            throw new IllegalArgumentException("lastName empty");
    }

    public String toString() {
        return firstName + " " + lastName +
                " is " + age + " years old";
    }
}
