package eu.javaspecialists.tjsn.issue279;

import java.util.*;
import java.util.concurrent.locks.*;

public class StampedLockDemo {
    public static void main(String... args) {
        var sl = new StampedLock();
        var stamps = new ArrayList<Long>();
        System.out.println(sl); // Unlocked
        for (int i = 0; i < 42; i++) {
            stamps.add(sl.readLock());
        }
        System.out.println(sl); // Read-Locks:42
        stamps.forEach(sl::unlockRead);
        System.out.println(sl); // Unlocked
        var stamp1 = sl.writeLock();
        System.out.println(sl); // Write-Locked
        var stamp2 = sl.writeLock(); // deadlocked
        System.out.println(sl); // Not seen...
    }
}
