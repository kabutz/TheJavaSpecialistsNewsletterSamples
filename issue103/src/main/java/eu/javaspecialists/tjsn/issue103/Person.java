package eu.javaspecialists.tjsn.issue103;

/**
 * @author Heinz Kabutz
 * @since 2005/02/07
 */
public class Person {
    private final String firstName;
    private final String surname;
    private final int age;

    public Person(String firstName, String surname, int age) {
        this.firstName = firstName;
        this.surname = surname;
        this.age = age;
    }

    @Override // always use this when you are overriding
    public String toString() {
        return firstName + " " + surname + ", " + age;
    }
}
