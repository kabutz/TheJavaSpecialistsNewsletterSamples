package eu.javaspecialists.tjsn.issue161;

import junit.framework.*;

import java.util.*;

public class HumanTest extends TestCase {
    public void testSingingAddingEnum() {
        EnumBuster<HumanState> buster =
                new EnumBuster<HumanState>(HumanState.class,
                        Human.class);

        try {
            Human heinz = new Human();
            heinz.sing(HumanState.HAPPY);
            heinz.sing(HumanState.SAD);

            HumanState MELLOW = buster.make("MELLOW");
            buster.addByValue(MELLOW);
            System.out.println(Arrays.toString(HumanState.values()));

            try {
                heinz.sing(MELLOW);
                fail("Should have caused an IllegalStateException");
            } catch (IllegalStateException success) {}
        } finally {
            System.out.println("Restoring HumanState");
            buster.restore();
            System.out.println(Arrays.toString(HumanState.values()));
        }
    }
}
