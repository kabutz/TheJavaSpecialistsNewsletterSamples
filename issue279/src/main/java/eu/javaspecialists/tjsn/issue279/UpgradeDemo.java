package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

// This deadlocks
public class UpgradeDemo {
    public static void main(String... args) {
        var rwlock = new ReentrantReadWriteLock();
        System.out.println(rwlock); // w=0, r=0
        rwlock.readLock().lock();
        System.out.println(rwlock); // w=0, r=1
        rwlock.writeLock().lock(); // deadlock
        System.out.println(rwlock);
        rwlock.readLock().unlock();
        System.out.println(rwlock);
        rwlock.writeLock().unlock();
        System.out.println(rwlock);
    }
}
