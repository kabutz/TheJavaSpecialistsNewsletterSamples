package eu.javaspecialists.tjsn.issue223;

import java.util.concurrent.locks.*;
import java.util.stream.*;

public class ManagedReentrantLockDemo {
  public static void main(String... args) {
    ReentrantLock lock = new ReentrantLock();
//    ReentrantLock lock = new ManagedReentrantLock();
    Condition condition = lock.newCondition();
    int upto = Runtime.getRuntime().availableProcessors() * 10;
    IntStream.range(0, upto).parallel().forEach(
        i -> {
          lock.lock();
          try {
            System.out.println(i + ": Got lock, now waiting - " +
                Thread.currentThread().getName());
            condition.awaitUninterruptibly();
          } finally {
            lock.unlock();
          }
        }
    );
  }
}
