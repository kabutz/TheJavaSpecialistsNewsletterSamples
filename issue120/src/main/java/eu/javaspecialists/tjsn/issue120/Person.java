package eu.javaspecialists.tjsn.issue120;

public class Person {
    private final String name;
    private final int age;
    private static final int MAXIMUM_AGE = 150;

    /**
     * Person constructor representing a natural
     * person.  Name may not be null.  Age must be
     * non-negative and less than MAXIMUM_AGE.
     *
     * @throws IllegalArgumentException if name is
     *                                  null or if age is out of range.
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        if (this.age < 0 || this.age > MAXIMUM_AGE) {
            throw new IllegalArgumentException(
                    "age out of range: " + this.age +
                            " expected range 0 <= age < " +
                            MAXIMUM_AGE);
        }
        if (this.name == null) {
            throw new IllegalArgumentException(
                    "name is null");
        }
    }
}
