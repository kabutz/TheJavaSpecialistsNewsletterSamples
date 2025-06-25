package eu.javaspecialists.tjsn.issue152;

import java.util.*;
import java.util.concurrent.*;

public class TestCorruption {
    private static final int THREADS = 2;
    private static final CountDownLatch latch =
            new CountDownLatch(THREADS);
    private static final BankAccount heinz =
            new BankAccountUnsynchronized(1000);
            // new BankAccountInvariantCheck(1000);
            // new BankAccountVolatile(1000);
            // new BankAccountSynchronized(1000);
            // new BankAccountReentrantLock(1000);
            // new BankAccountReentrantReadWriteLock(1000);

    public static void main(String... args) {
        for (int i = 0; i < THREADS; i++) {
            addThread();
        }
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(heinz.getBalance());
            }
        }, 100, 1000);
    }

    private static void addThread() {
        new Thread() {
            {start();}

            public void run() {
                latch.countDown();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    return;
                }
                while (true) {
                    heinz.deposit(100);
                    heinz.withdraw(100);
                }
            }
        };
    }
}
