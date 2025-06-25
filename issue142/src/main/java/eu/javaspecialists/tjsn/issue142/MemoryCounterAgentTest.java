package eu.javaspecialists.tjsn.issue142;

import java.util.*;
import java.util.concurrent.locks.*;

public class MemoryCounterAgentTest {
    public static void measureSize(Object o) {
        long memShallow = MemoryCounterAgent.sizeOf(o);
        long memDeep = MemoryCounterAgent.deepSizeOf(o);
        System.out.printf("%s, shallow=%d, deep=%d%n",
                o.getClass().getSimpleName(),
                memShallow, memDeep);
    }

    public static void main(String... args) {
        measureSize(new Object());
        measureSize(new HashMap());
        measureSize(new LinkedHashMap());
        measureSize(new ReentrantReadWriteLock());
        measureSize(new byte[1000]);
        measureSize(new boolean[1000]);
        measureSize(new String("Hello World".toCharArray()));
        measureSize("Hello World");
        measureSize(10);
        measureSize(100);
        measureSize(1000);
        measureSize(new Parent());
        measureSize(new Kid());
        measureSize(Thread.State.TERMINATED);
    }

    private static class Parent {
        private int i;
        private boolean b;
        private long l;
    }

    private static class Kid extends Parent {
        private boolean b;
        private float f;
    }
}
