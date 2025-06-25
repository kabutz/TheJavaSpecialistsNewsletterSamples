package eu.javaspecialists.tjsn.issue152;

public class BankAccountInvariantCheck implements BankAccount {
    private int balance;

    public BankAccountInvariantCheck(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        int check = balance + amount;
        balance = check;
        if (balance != check) {
            throw new AssertionError("Data Race Detected");
        }
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public int getBalance() {
        return balance;
    }
}
