package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

// This runs through fine
public class DowngradeDemo {
    public static void main(String... args) {
        var rwlock = new ReentrantReadWriteLock();
        System.out.println(rwlock); // w=0, r=0
        rwlock.writeLock().lock();
        System.out.println(rwlock); // w=1, r=0
        rwlock.readLock().lock();
        System.out.println(rwlock); // w=1, r=1
        rwlock.writeLock().unlock();
        // at this point other threads can also acquire read locks
        System.out.println(rwlock); // w=0, r=1
        rwlock.readLock().unlock();
        System.out.println(rwlock); // w=0, r=0
    }
}
