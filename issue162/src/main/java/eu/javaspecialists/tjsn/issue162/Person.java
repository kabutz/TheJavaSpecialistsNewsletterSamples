package eu.javaspecialists.tjsn.issue162;

public class Person {
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;

    private final int age;
    private final String name;

    /**
     * Constructs a person with the given age and name.
     *
     * @param age  The age must fit into a range, specified by
     *             MIN_AGE and MAX_AGE
     * @param name The name should not be null, nor an empty
     *             string
     * @throws OutOfRangeException      if the age is out of range.
     *                                  The age may not be less
     *                                  than the constant MIN_AGE
     *                                  and may not be more than
     *                                  the constant MAX_AGE.
     * @throws IllegalArgumentException if the name is null or
     *                                  empty.
     * @see #MIN_AGE, #MAX_AGE
     */
    public Person(int age, String name) {
        this.age = age;
        this.name = name;
        if (this.age < MIN_AGE || this.age > MAX_AGE) {
            throw new OutOfRangeException(this.age, MIN_AGE, MAX_AGE);
        }
        if (this.name == null || this.name.equals("")) {
            throw new IllegalArgumentException(
                    "name parameter should not be null nor empty");
        }
    }

    public String toString() {
        return "Person " + name + " is " + age + " years old";
    }
}
