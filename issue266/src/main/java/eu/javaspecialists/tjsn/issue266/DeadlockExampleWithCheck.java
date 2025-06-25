package eu.javaspecialists.tjsn.issue266;

import java.lang.management.*;

public class DeadlockExampleWithCheck extends Thread {
    private final Object monitor1, monitor2;

    public DeadlockExampleWithCheck(Object monitor1,
                                    Object monitor2) {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
    }

    public void run() {
        while (true) {
            synchronized (monitor1) {
                synchronized (monitor2) {
                    System.out.println("No deadlock yet ...");
                }
            }
        }
    }

    public static void main(String... args) {
        var a = new Object();
        var b = new Object();
        new DeadlockExampleWithCheck(a, b).start();
        new DeadlockExampleWithCheck(b, a).start();
        ThreadMXBean tb = ManagementFactory.getThreadMXBean();
        while (true) {
            long[] deadlock = tb.findDeadlockedThreads();
            if (deadlock != null && deadlock.length > 0) {
                System.out.println("Deadlock detected, exiting");
                System.exit(1);
            }
        }
    }
}
