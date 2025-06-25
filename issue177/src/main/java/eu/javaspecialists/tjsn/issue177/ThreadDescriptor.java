package eu.javaspecialists.tjsn.issue177;

import java.util.concurrent.atomic.*;

/**
 * Idea based on JavaDoc in java.lang.ThreadLocal, except that
 * this code is correct (I think) ;-)
 */
public class ThreadDescriptor {
    private static final AtomicInteger uniqueId =
            new AtomicInteger(0);
    private static final ThreadLocal<String> descriptor
            = new ThreadLocal<String>() {
        protected String initialValue() {
            return Thread.currentThread().getName() + "_utid={" +
                    uniqueId.getAndIncrement() + "}";
        }
    };

    public static String get() {
        return descriptor.get();
    }
}
