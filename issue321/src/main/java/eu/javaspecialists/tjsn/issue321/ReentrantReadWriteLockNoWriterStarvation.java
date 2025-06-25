package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.locks.*;

public class ReentrantReadWriteLockNoWriterStarvation {
    public static void main(String... args) {
        new WriteLockStarvationDemo(
                new ReentrantReadWriteLock()
        ).run();
    }
}
