package eu.javaspecialists.tjsn.issue225.take2;

import java.io.*;

public final class Person implements Serializable,
        ObjectInputValidation {
    private final String firstName;
    private final String lastName;
    private final int age;

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        try {
            validateObject();
        } catch (InvalidObjectException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void validateObject() throws InvalidObjectException {
        if (age < 0)
            throw new InvalidObjectException("age negative");
        if (age > 150)
            throw new InvalidObjectException("age more than 150");
        if (firstName == null || firstName.isEmpty())
            throw new InvalidObjectException("firstName empty");
        if (lastName == null || lastName.isEmpty())
            throw new InvalidObjectException("lastName empty");
    }

    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.registerValidation(this, 0);
        in.defaultReadObject();
    }

    public String toString() {
        return firstName + " " + lastName +
                " is " + age + " years old";
    }
}
