package eu.javaspecialists.tjsn.issue213;

import java.util.concurrent.*;

public class WaitNotifyLiveLockTest {
    public static void main(String... args) throws Exception {
        // Local variables and parameters accessed from inner classes
        // in Java 8 do not need to be explicitly declared as final!
        // They are implicitely final.
        WaitNotifyLivelock wnll = new WaitNotifyLivelock();
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<?> waitForFuture = pool.submit(wnll::waitFor);
        // "wnll::waitFor" is a Java 8 method reference and is
        // roughly equivalent to:
        // pool.submit(new Runnable() {
        //   public void run() {
        //     wnll.waitFor();
        //   }
        // });
        while (WaitNotifyLivelock.waitingThread == null) {
            Thread.sleep(10);
        }
        // now we interrupt the thread waiting for the signal
        WaitNotifyLivelock.waitingThread.interrupt();

        Future<?> notifyFuture = pool.submit(wnll::notifyIt);

        try {
            notifyFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.err.println("notifyFuture could not complete");
        }
        try {
            waitForFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.err.println("waitForFuture could not complete");
            System.out.println("Waiting thread state: " +
                    WaitNotifyLivelock.waitingThread.getState());
        }
    }
}
