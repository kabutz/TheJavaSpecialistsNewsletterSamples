package eu.javaspecialists.tjsn.issue088;

public class Person implements java.io.Serializable {
    private final String firstName;
    private final String surname;
    private int age;

    public Person(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }

    public String toString() {
        return firstName + " " + surname + ", " + age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}