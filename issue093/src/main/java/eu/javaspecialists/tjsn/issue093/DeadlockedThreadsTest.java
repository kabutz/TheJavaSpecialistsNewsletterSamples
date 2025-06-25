package eu.javaspecialists.tjsn.issue093;

import java.lang.management.*;

public class DeadlockedThreadsTest {
    public static void main(String... args) {
        ThreadWarningSystem tws = new ThreadWarningSystem();
        tws.addListener(new ThreadWarningSystem.Listener() {
            public void deadlockDetected(ThreadInfo inf) {
                System.out.println("Deadlocked Thread:");
                System.out.println("------------------");
                System.out.println(inf);
                for (StackTraceElement ste : inf.getStackTrace()) {
                    System.out.println("\t" + ste);
                }
            }

            public void thresholdExceeded(ThreadInfo[] threads) {
            }
        });

        // deadlock with three locks
        Object lock1 = new String("lock1");
        Object lock2 = new String("lock2");
        Object lock3 = new String("lock3");

        new DeadlockingThread("t1", lock1, lock2);
        new DeadlockingThread("t2", lock2, lock3);
        new DeadlockingThread("t3", lock3, lock1);

        // deadlock with two locks
        Object lock4 = new String("lock4");
        Object lock5 = new String("lock5");

        new DeadlockingThread("t4", lock4, lock5);
        new DeadlockingThread("t5", lock5, lock4);
    }

    // There is absolutely nothing you can do when you have
    // deadlocked threads.  You cannot stop them, you cannot
    // interrupt them, you cannot tell them to stop trying to
    // get a lock, and you also cannot tell them to let go of
    // the locks that they own.
    private static class DeadlockingThread extends Thread {
        private final Object lock1;
        private final Object lock2;

        public DeadlockingThread(String name, Object lock1, Object lock2) {
            super(name);
            this.lock1 = lock1;
            this.lock2 = lock2;
            start();
        }

        public void run() {
            while (true) {
                f();
            }
        }

        private void f() {
            synchronized (lock1) {
                g();
            }
        }

        private void g() {
            synchronized (lock2) {
                // do some work...
                for (int i = 0; i < 1000 * 1000; i++) ;
            }
        }
    }
}
