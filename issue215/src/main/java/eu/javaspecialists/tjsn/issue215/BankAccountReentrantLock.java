package eu.javaspecialists.tjsn.issue215;

import java.util.concurrent.locks.*;

public class BankAccountReentrantLock {
    private final Lock lock = new ReentrantLock();
    private long balance;

    public BankAccountReentrantLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(long amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    public long getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
