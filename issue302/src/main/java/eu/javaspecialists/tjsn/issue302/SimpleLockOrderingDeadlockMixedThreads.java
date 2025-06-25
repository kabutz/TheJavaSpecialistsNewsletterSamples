package eu.javaspecialists.tjsn.issue302;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Phaser;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleLockOrderingDeadlockMixedThreads {
  public static void main(String... args)
      throws InterruptedException {
    var monitor1 = new Object();
    var monitor2 = new Object();
    var coop = new Phaser(2);
    Thread.ofPlatform().name("platform").start(() -> {
      synchronized (monitor1) {
        coop.arriveAndAwaitAdvance();
        synchronized (monitor2) {
          System.out.println("All's well");
        }
      }
    });
    Thread.ofVirtual().name("virtual").start(() -> {
      synchronized (monitor2) {
        coop.arriveAndAwaitAdvance();
        synchronized (monitor1) {
          System.out.println("All's well too");
        }
      }
    });
    Thread.sleep(100);
    ThreadMXBean tmb = ManagementFactory.getThreadMXBean();
    long[] deadlocks = tmb.findDeadlockedThreads();
    System.out.println("deadlocks = " + deadlocks);
  }
}
