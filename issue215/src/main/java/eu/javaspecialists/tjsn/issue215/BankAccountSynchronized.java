package eu.javaspecialists.tjsn.issue215;

public class BankAccountSynchronized {
    private long balance;

    public BankAccountSynchronized(long balance) {
        this.balance = balance;
    }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public synchronized void withdraw(long amount) {
        balance -= amount;
    }

    public synchronized long getBalance() {
        return balance;
    }
}
