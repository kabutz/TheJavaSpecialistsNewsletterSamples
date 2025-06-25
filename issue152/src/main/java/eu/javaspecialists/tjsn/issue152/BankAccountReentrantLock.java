package eu.javaspecialists.tjsn.issue152;

import java.util.concurrent.locks.*;

public class BankAccountReentrantLock implements BankAccount {
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccountReentrantLock(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            int check = balance + amount;
            balance = check;
            if (balance != check) {
                throw new AssertionError("Data Race Detected");
            }
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
