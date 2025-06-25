package eu.javaspecialists.tjsn.issue215;

public class BankAccountSynchronizedVolatile {
    private volatile long balance;

    public BankAccountSynchronizedVolatile(long balance) {
        this.balance = balance;
    }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public synchronized void withdraw(long amount) {
        balance -= amount;
    }

    public long getBalance() {
        return balance;
    }
}
