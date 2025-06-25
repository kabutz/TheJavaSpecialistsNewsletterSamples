package eu.javaspecialists.tjsn.issue279;

import java.util.concurrent.locks.*;

public class UpgradeDemoKotlinAsJava {
    public static void main(String... args) {
        var rwlock = new ReentrantReadWriteLock();
        System.out.println(rwlock); // w=0, r=0
        rwlock.readLock().lock();
        try {
            System.out.println(rwlock); // w=0, r=1
            int readCount = rwlock.getWriteHoldCount() == 0
                    ? rwlock.getReadHoldCount() : 0;
            for (int i = 0; i < readCount; i++)
                rwlock.readLock().unlock();
            rwlock.writeLock().lock();
            try {
                System.out.println(rwlock); // w=1, r=0
            } finally {
                for (int i = 0; i < readCount; i++)
                    rwlock.readLock().lock();
                rwlock.writeLock().unlock();
            }
            System.out.println(rwlock); // w=0, r=1
        } finally {
            rwlock.readLock().unlock();
        }
        System.out.println(rwlock); // w=0, r=0
    }
}
