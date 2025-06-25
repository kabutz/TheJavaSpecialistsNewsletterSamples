package eu.javaspecialists.tjsn.issue155;

import java.util.concurrent.atomic.*;

public class BankAccount {
    private final AtomicInteger balance =
            new AtomicInteger();

    public BankAccount(int balance) {
        this.balance.set(balance);
    }

    public void deposit(int amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public int getBalance() {
        return balance.intValue();
    }
}
