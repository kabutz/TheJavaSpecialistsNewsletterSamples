package eu.javaspecialists.tjsn.issue228;

import java.util.*;
import java.util.concurrent.*;

public class JobDiscovererTest {
    public static void main(String... args) {
        final CountDownLatch latch = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            pool.submit(new Runnable() {
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException consumeAndExit) {
                        System.out.println(Thread.currentThread().getName() +
                                " was interrupted - exiting");
                    }
                }

                public String toString() {
                    return "Runnable: " + finalI;
                }
            });
            pool.submit(new Callable<String>() {
                public String call() throws InterruptedException {
                    latch.await();
                    return "success";
                }

                public String toString() {
                    return "Callable: " + finalI;
                }
            });
        }

        // Note: the Runnables returned from shutdownNow are NOT
        // the same objects as we submitted to the pool!!!
        List<Runnable> tasks = pool.shutdownNow();

        System.out.println("Tasks from ThreadPool");
        System.out.println("=====================");
        for (Runnable task : tasks) {
            System.out.println("Task from ThreadPool " + task);
        }

        System.out.println();
        System.out.println("Using our JobDiscoverer");
        System.out.println("=======================");

        for (Runnable task : tasks) {
            Object realTask = JobDiscoverer.findRealTask(task);
            System.out.println("Real task was actually " + realTask);
        }
    }
}
