package eu.javaspecialists.tjsn.issue266;

import java.lang.management.*;

public class DeadlockExampleOrdering extends Thread {
    private final Object monitor1, monitor2;
    private static final Object TIE_MONITOR = new Object();

    public DeadlockExampleOrdering(Object monitor1,
                                   Object monitor2) {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
    }

    public void run() {
        Runnable job = () -> System.out.println("No deadlock ...");
        int id1 = System.identityHashCode(monitor1);
        int id2 = System.identityHashCode(monitor2);
        while (true) {
            if (id1 < id2) {
                synchronized (monitor1) {
                    synchronized (monitor2) {
                        execute(job);
                    }
                }
            } else if (id1 > id2) {
                synchronized (monitor2) {
                    synchronized (monitor1) {
                        execute(job);
                    }
                }
            } else {
                synchronized (TIE_MONITOR) {
                    synchronized (monitor1) {
                        synchronized (monitor2) {
                            execute(job);
                        }
                    }
                }
            }
        }
    }

    private void execute(Runnable job) {
        assert Thread.holdsLock(monitor1) : "monitor1 not locked";
        assert Thread.holdsLock(monitor2) : "monitor2 not locked";
        job.run();
    }

    public static void main(String... args) {
        var a = new Object();
        var b = new Object();
        new DeadlockExampleOrdering(a, b).start();
        new DeadlockExampleOrdering(b, a).start();
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
