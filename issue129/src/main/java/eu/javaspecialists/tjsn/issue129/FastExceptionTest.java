package eu.javaspecialists.tjsn.issue129;

import com.jamonapi.*;

public class FastExceptionTest {
    public static final int COUNT = 1000 * 1000;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test(new SlowExceptionThrower());
            test(new FastExceptionThrower());
            test(new SuperFastExceptionThrower());
        }
    }

    private static void test(Thrower t) {
        Monitor mon = MonitorFactory.start(t.getClass().getName());
        for (int i = 0; i < COUNT; i++) {
            try {
                t.causeException();
            } catch (Exception ex) {}
        }
        mon.stop();
        System.out.println(mon);
    }
}
