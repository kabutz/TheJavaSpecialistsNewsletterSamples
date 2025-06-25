package eu.javaspecialists.tjsn.issue162;

import junit.framework.*;

public class PersonTest extends TestCase {
    public void testExceptions() {
        new Person(36, "Heinz");
        new Person(Person.MIN_AGE, "Heinz");
        new Person(Person.MAX_AGE, "Heinz");
        try {
            new Person(Person.MIN_AGE - 1, "Heinz");
            fail("Allowed setting of out of range age");
        } catch (OutOfRangeException success) {
        }
        try {
            new Person(Person.MAX_AGE + 1, "Heinz");
            fail("Allowed setting of out of range age");
        } catch (OutOfRangeException success) {
        }
        try {
            new Person(Integer.MAX_VALUE, "Heinz");
            fail("Allowed setting of out of range age");
        } catch (OutOfRangeException success) {
        }
        try {
            new Person(Integer.MIN_VALUE, "Heinz");
            fail("Allowed setting of out of range age");
        } catch (OutOfRangeException success) {
        }
    }
}