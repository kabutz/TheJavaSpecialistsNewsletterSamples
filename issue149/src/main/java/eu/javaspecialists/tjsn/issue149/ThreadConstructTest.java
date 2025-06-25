package eu.javaspecialists.tjsn.issue149;

import java.util.concurrent.*;

public class ThreadConstructTest {
    private static final int NUMBER_OF_THREADS = 100 * 1000;
    private Semaphore semaphore = new Semaphore(10);
    private final Runnable job = new Runnable() {
        public void run() {
            semaphore.release();
        }
    };

    public void noThreadPool() throws InterruptedException {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            semaphore.acquire();
            new Thread(job).start();
        }
        // wait for all jobs to finish
        semaphore.acquire(10);
        semaphore.release(10);
    }

    public void fixedThreadPool() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(12);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            semaphore.acquire();
            pool.submit(job);
        }
        semaphore.acquire(10);
        semaphore.release(10);
        pool.shutdown();
    }

    public static void main(String... args) throws Exception {
        ThreadConstructTest test = new ThreadConstructTest();

        long time = System.currentTimeMillis();
        test.noThreadPool();
        System.out.println(System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        test.fixedThreadPool();
        System.out.println(System.currentTimeMillis() - time);
    }
}
