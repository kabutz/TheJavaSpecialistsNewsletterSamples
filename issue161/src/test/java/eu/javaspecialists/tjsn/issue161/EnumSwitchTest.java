package eu.javaspecialists.tjsn.issue161;

import junit.framework.*;

public class EnumSwitchTest extends TestCase {
    public void testSingingDeletingEnum() {
        EnumBuster<HumanState> buster =
                new EnumBuster<HumanState>(HumanState.class,
                        EnumSwitchTest.class);
        try {
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case HAPPY:
                    case SAD:
                        break;
                    default:
                        fail("Unknown state");
                }
            }

            buster.deleteByValue(HumanState.HAPPY);
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case SAD:
                        break;
                    case HAPPY:
                    default:
                        fail("Unknown state");
                }
            }

            buster.undo();
            buster.deleteByValue(HumanState.SAD);
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case HAPPY:
                        break;
                    case SAD:
                    default:
                        fail("Unknown state");
                }
            }

            buster.deleteByValue(HumanState.HAPPY);
            for (HumanState state : HumanState.values()) {
                switch (state) {
                    case HAPPY:
                    case SAD:
                    default:
                        fail("Unknown state");
                }
            }
        } finally {
            buster.restore();
        }
    }
}
