package eu.javaspecialists.tjsn.issue164;

import junit.framework.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class ThreadLocalTest extends TestCase {
    private void collectGarbage() {
        for (int i = 0; i < 3; i++) {
            System.gc();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void test1() {
        AtomicBoolean threadLocalUserFinalized = new AtomicBoolean(false);
        AtomicBoolean myThreadLocalFinalized = new AtomicBoolean(false);
        ThreadLocalUser user = new ThreadLocalUser(threadLocalUserFinalized,
                myThreadLocalFinalized);
        AtomicBoolean myValueFinalized = new AtomicBoolean(false);
        MyValue value = new MyValue(10_000, myValueFinalized);
        user.setThreadLocal(value);
        user.clear();
        value = null;
        collectGarbage();
        assertTrue(myValueFinalized.get());
        assertFalse(threadLocalUserFinalized.get());
        assertFalse(myThreadLocalFinalized.get());
    }

    // weird case
    public void test2() {
        AtomicBoolean threadLocalUserFinalized = new AtomicBoolean(false);
        AtomicBoolean myThreadLocalFinalized = new AtomicBoolean(false);
        ThreadLocalUser user = new ThreadLocalUser(threadLocalUserFinalized,
                myThreadLocalFinalized);
        AtomicBoolean myValueFinalized = new AtomicBoolean(false);
        MyValue value = new MyValue(20_000, myValueFinalized);
        user.setThreadLocal(value);
        value = null;
        user = null;
        collectGarbage();
        assertFalse(myValueFinalized.get());
        assertTrue(threadLocalUserFinalized.get());
        assertTrue(myThreadLocalFinalized.get());
    }


    public void test3() throws InterruptedException {
        AtomicBoolean myThreadFinalized = new AtomicBoolean(false);
        AtomicBoolean threadLocalUserFinalized = new AtomicBoolean(false);
        AtomicBoolean myThreadLocalFinalized = new AtomicBoolean(false);
        AtomicBoolean myValueFinalized = new AtomicBoolean(false);

        Thread t = new MyThread(new Runnable() {
            public void run() {
                ThreadLocalUser user = new ThreadLocalUser(threadLocalUserFinalized,
                        myThreadLocalFinalized);
                MyValue value = new MyValue(30_000, myValueFinalized);
                user.setThreadLocal(value);
            }
        }, myThreadFinalized);
        t.start();
        t.join();
        collectGarbage();
        assertTrue(myValueFinalized.get());
        assertTrue(threadLocalUserFinalized.get());
        assertTrue(myThreadLocalFinalized.get());
        assertFalse(myThreadFinalized.get());
    }

    public void test4() throws InterruptedException {
        AtomicBoolean threadLocalUserFinalized = new AtomicBoolean(false);
        AtomicBoolean myThreadLocalFinalized = new AtomicBoolean(false);
        AtomicBoolean myValueFinalized = new AtomicBoolean(false);
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        singlePool.execute(new Runnable() {
            public void run() {
                ThreadLocalUser user = new ThreadLocalUser(threadLocalUserFinalized,
                        myThreadLocalFinalized);
                MyValue value = new MyValue(40_000, myValueFinalized);
                user.setThreadLocal(value);
            }
        });
        Thread.sleep(100);
        collectGarbage();
        assertFalse(myValueFinalized.get());
        assertTrue(threadLocalUserFinalized.get());
        assertTrue(myThreadLocalFinalized.get());
        singlePool.shutdown();
        while (!singlePool.awaitTermination(1, TimeUnit.SECONDS)) ;
    }

    public void test5() throws Exception {
        AtomicBoolean threadLocalUserFinalized = new AtomicBoolean(false);
        AtomicBoolean myThreadLocalFinalized = new AtomicBoolean(false);
        AtomicBoolean myValueFinalized = new AtomicBoolean(false);

        for (int i = 0; i < 100; i++) {
            ThreadLocalUser user = new ThreadLocalUser(i, threadLocalUserFinalized, myThreadLocalFinalized);
            MyValue value = new MyValue(50_000 + i, myValueFinalized);
            user.setThreadLocal(value);
            value = null;
            user = null;
        }
        collectGarbage();

        System.out.println("myValueFinalized.get() = " + myValueFinalized.get());
        assertFalse(myValueFinalized.get());
        assertTrue(threadLocalUserFinalized.get());
        assertTrue(myThreadLocalFinalized.get());
    }

    public void test6() throws Exception {
        AtomicBoolean threadLocalUserFinalized = new AtomicBoolean(false);
        AtomicBoolean myThreadLocalFinalized = new AtomicBoolean(false);
        AtomicBoolean myValueFinalized = new AtomicBoolean(false);

        for (int i = 0; i < 100; i++) {
            ThreadLocalUser user = new ThreadLocalUser(i, threadLocalUserFinalized, myThreadLocalFinalized);
            MyValue value = new MyValue(60_000 + i, myValueFinalized);
            user.setThreadLocal(value);
            value = null;
            user = null;
            collectGarbage();
        }

        assertTrue(myValueFinalized.get());
        assertTrue(threadLocalUserFinalized.get());
        assertTrue(myThreadLocalFinalized.get());
    }
}