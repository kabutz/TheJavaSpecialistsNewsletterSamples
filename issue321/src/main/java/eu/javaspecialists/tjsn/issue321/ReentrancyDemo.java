package eu.javaspecialists.tjsn.issue321;

import java.util.concurrent.locks.*;

public class ReentrancyDemo {
    public static void main(String... args) {
        test(new ReentrantReadWriteLock());
        test(new StampedLock().asReadWriteLock());
    }

    private static void test(ReadWriteLock rwlock) {
        System.out.println(rwlock.getClass().getTypeName());
        rwlock.writeLock().lock();
        try {
            System.out.println("Acquired write lock #1");
            inner(rwlock);
        } finally {
            rwlock.writeLock().unlock();
        }
        System.out.println();
    }

    private static void inner(ReadWriteLock rwlock) {
        if (rwlock.writeLock().tryLock()) {
            try {
                System.out.println("Acquired write lock #2");
            } finally {
                rwlock.writeLock().unlock();
            }
        } else {
            System.out.println("Failed to acquire write lock #2");
        }
    }
}
