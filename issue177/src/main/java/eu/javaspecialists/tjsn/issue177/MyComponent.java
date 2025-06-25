package eu.javaspecialists.tjsn.issue177;

import java.util.concurrent.atomic.*;
import java.util.logging.*;

public class MyComponent {
    private static final Logger log =
            Logger.getLogger(MyComponent.class.getName());

    private AtomicInteger fooCalls = new AtomicInteger();

    public void foo() {
        log.info("Number of calls to foo(): " +
                fooCalls.incrementAndGet());
    }

    public static void main(String... args) {
        MyComponent mc = new MyComponent();
        mc.foo();
        mc.foo();
        mc.foo();

        log.setLevel(Level.SEVERE);

        testPerformance(mc);
        testPerformance(mc);
        testPerformance(mc);
    }

    private static void testPerformance(MyComponent mc) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1000 * 1000; i++) {
            mc.foo();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
