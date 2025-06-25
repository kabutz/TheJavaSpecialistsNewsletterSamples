package eu.javaspecialists.tjsn.issue096;

public class Person {
    private final String name;
    private final int age;
    private final int iq = 110;
    private final Object country = "South Africa";

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name + ", " + age + " of IQ=" + iq + " from " + country;
    }
}
