package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

public class StampedLockDowngradeDemo {
    public static void main(String... args) {
        var sl = new StampedLock();
        System.out.println(sl); // Unlocked
        long wstamp = sl.writeLock();
        System.out.println(sl); // Write-locked
        long rstamp = sl.tryConvertToReadLock(wstamp);
        if (rstamp != 0) {
            System.out.println("Converted write to read");
            System.out.println(sl); // Read-locks:1
            sl.unlockRead(rstamp);
            System.out.println(sl); // Unlocked
        } else { // this cannot happen (famous last words)
            sl.unlockWrite(wstamp);
            throw new AssertionError("Failed to downgrade lock");
        }
    }
}
