package eu.javaspecialists.tjsn.issue299;

// NOT thread-safe
public class BankAccount {
    private Double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        synchronized (balance) { // never lock on primitive wrapper!
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        deposit(-amount);
    }

    public double getBalance() {
        synchronized (balance) {
            return balance;
        }
    }
}
