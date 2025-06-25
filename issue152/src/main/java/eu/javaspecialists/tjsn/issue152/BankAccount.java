package eu.javaspecialists.tjsn.issue152;

public interface BankAccount {
    void deposit(int amount);

    void withdraw(int amount);

    int getBalance();
}
