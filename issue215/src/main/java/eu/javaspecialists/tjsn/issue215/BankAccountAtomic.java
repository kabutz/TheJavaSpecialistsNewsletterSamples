package eu.javaspecialists.tjsn.issue215;

import java.util.concurrent.atomic.*;

public class BankAccountAtomic {
    private final AtomicLong balance;

    public BankAccountAtomic(long balance) {
        this.balance = new AtomicLong(balance);
    }

    public void deposit(long amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(long amount) {
        balance.addAndGet(-amount);
    }

    public long getBalance() {
        return balance.get();
    }
}
