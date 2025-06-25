package eu.javaspecialists.tjsn.issue152;

import java.util.concurrent.locks.*;

public class BankAccountReentrantReadWriteLock implements BankAccount {
    private int balance;
    private final ReadWriteLock lock =
            new ReentrantReadWriteLock();

    public BankAccountReentrantReadWriteLock(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount)  {
        lock.writeLock().lock();
        try {
            int check = balance + amount;
            balance = check;
            if (balance != check) {
                throw new AssertionError("Data Race Detected");
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void withdraw(int amount)  {
        deposit(-amount);
    }

    public int getBalance()  {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }
}
