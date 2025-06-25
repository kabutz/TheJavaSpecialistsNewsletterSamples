package eu.javaspecialists.tjsn.issue299;

import java.util.stream.*;

public class BankAccountDemo {
    public static void main(String... args) {
        var account = new BankAccount(1000);
        IntStream.range(0, 1_000_000)
                .parallel()
                .forEach(i -> {
                    account.deposit(100);
                    account.withdraw(100);
                });
        System.out.println(account.getBalance());
    }
}
