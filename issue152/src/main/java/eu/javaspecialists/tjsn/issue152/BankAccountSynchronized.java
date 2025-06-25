package eu.javaspecialists.tjsn.issue152;

public class BankAccountSynchronized implements BankAccount {
    private int balance;
    private final Object lock = new Object();

    public BankAccountSynchronized(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        synchronized (lock) {
            int check = balance + amount;
            balance = check;
            if (balance != check) {
                throw new AssertionError("Data Race Detected");
            }
        }
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public int getBalance() {
        synchronized (lock) {
            return balance;
        }
    }
}
