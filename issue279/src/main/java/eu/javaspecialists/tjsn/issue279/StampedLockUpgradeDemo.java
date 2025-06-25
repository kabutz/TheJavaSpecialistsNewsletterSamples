package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

public class StampedLockUpgradeDemo {
    public static void main(String... args) {
        var sl = new StampedLock();
        System.out.println(sl); // Unlocked
        long rstamp = sl.readLock();
        System.out.println(sl); // Read-locks:1
        long wstamp = sl.tryConvertToWriteLock(rstamp);
        if (wstamp != 0) {
            // works if no one else has a read-lock
            System.out.println("Converted read to write");
            System.out.println(sl); // Write-locked
            sl.unlockWrite(wstamp);
        } else {
            // we do not have an exclusive hold on read-lock
            System.out.println("Could not convert read to write");
            sl.unlockRead(rstamp);
        }
        System.out.println(sl); // Unlocked
    }
}
