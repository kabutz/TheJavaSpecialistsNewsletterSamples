package eu.javaspecialists.tjsn.issue114;

import junit.framework.*;

public class CarTest extends TestCase {
    public void testIncorrect() {
        assertEquals(
                new Car("CET192233"),
                new Car("CET192233"));
    }

    public void testCorrect() {
        assertEquals(
                new Car(new String("CET192233")),
                new Car("CET192233"));
    }
}