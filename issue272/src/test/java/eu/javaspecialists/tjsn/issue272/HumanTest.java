package eu.javaspecialists.tjsn.issue272;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// Run with --add-opens java.base/java.lang=ALL-UNNAMED
public class HumanTest {
    @Test
    public void testSingingAddingEnum()
            throws ReflectiveOperationException {
        EnumBuster<HumanState> buster =
                new EnumBuster<>(HumanState.class, Human.class);
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
