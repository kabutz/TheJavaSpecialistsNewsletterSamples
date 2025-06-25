package eu.javaspecialists.tjsn.issue276;

import java.util.*;

public class PersonClass
        implements Person, java.io.Serializable {
    private final String firstName;
    private final String lastName;

    public PersonClass(String firstName) {
        this(firstName, null);
    }

    public PersonClass(String firstName,
                       String lastName) {
        if ("Heinz".equals(firstName))
            throw new IllegalArgumentException(
                    "\"%s\" is trademarked".formatted(firstName));
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PersonClass that = (PersonClass) o;
        return Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName);
    }

    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public String toString() {
        return "PersonClass{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
