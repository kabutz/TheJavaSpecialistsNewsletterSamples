package eu.javaspecialists.tjsn.issue177;

import java.util.concurrent.*;

public class ThreadDescriptorTest {
    public static void main(String... args) {
        Runnable task = new Runnable() {
            public void run() {
                System.out.println(ThreadDescriptor.get());
            }
        };
        new Thread(task, "MyThread").start();
        System.out.println(ThreadDescriptor.get());
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(task);
        pool.submit(task);
        pool.submit(task);
        pool.shutdown();
    }
}
