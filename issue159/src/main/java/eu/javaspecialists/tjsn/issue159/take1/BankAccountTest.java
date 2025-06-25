package eu.javaspecialists.tjsn.issue159.take1;

import java.util.concurrent.*;

public class BankAccountTest {
    public static void main(String... args) {
        final BankAccount account = new BankAccount(1000);
        for (int i = 0; i < 2; i++) {
            new Thread() {
                {
                    start();
                }

                public void run() {
                    while (true) {
                        account.deposit(100);
                        account.withdraw(100);
                    }
                }
            };
        }
        ScheduledExecutorService timer =
                Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println(account.getBalance());
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
