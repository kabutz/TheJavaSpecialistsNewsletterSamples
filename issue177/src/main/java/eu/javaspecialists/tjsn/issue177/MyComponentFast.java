package eu.javaspecialists.tjsn.issue177;

import java.util.concurrent.atomic.*;
import java.util.logging.*;

public class MyComponentFast {
    private static final Logger log =
            Logger.getLogger(MyComponentFast.class.getName());

    private AtomicInteger fooCalls = new AtomicInteger();

    public void foo() {
        if (log.isLoggable(Level.INFO)) {
            log.info("Number of calls to foo(): " +
                    fooCalls.incrementAndGet());
        }
    }

    public static void main(String... args) {
        MyComponentFast mc = new MyComponentFast();
        mc.foo();
        mc.foo();
        mc.foo();

        log.setLevel(Level.SEVERE);

        testPerformance(mc);
        testPerformance(mc);
        testPerformance(mc);
    }

    private static void testPerformance(MyComponentFast mc) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1000 * 1000; i++) {
            mc.foo();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
