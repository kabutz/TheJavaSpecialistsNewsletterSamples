package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

public class StampedLockDowngradeFailureDemo {
    public static void main(String... args) {
        var sl = new StampedLock();
        System.out.println(sl); // Unlocked
        long wstamp = sl.writeLock();
        System.out.println(sl); // Write-Locked
        long rstamp = sl.readLock(); // deadlocked
        System.out.println(sl); // Not seen...
    }
}
