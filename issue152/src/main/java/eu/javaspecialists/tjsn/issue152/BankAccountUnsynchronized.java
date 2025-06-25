package eu.javaspecialists.tjsn.issue152;

public class BankAccountUnsynchronized implements BankAccount {
    private int balance;

    public BankAccountUnsynchronized(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public int getBalance() {
        return balance;
    }
}
