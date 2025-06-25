package eu.javaspecialists.tjsn.issue162;

import org.junit.*;

public class PersonTestJUnit4 {
    @Test
    public void correctAges() {
        new Person(36, "Heinz");
        new Person(Person.MIN_AGE, "Heinz");
        new Person(Person.MAX_AGE, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void tooYoung() {
        new Person(Person.MIN_AGE - 1, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void tooOld() {
        new Person(Person.MAX_AGE + 1, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void muchTooOld() {
        new Person(Integer.MAX_VALUE, "Heinz");
    }

    @Test(expected = OutOfRangeException.class)
    public void muchTooYoung() {
        new Person(Integer.MIN_VALUE, "Heinz");
    }
}
