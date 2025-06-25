package eu.javaspecialists.tjsn.issue144;

import java.util.*;

public class BetterWorker {
    private volatile boolean quittingTime = false;
    private final Object quittingTimeLock = new Object();
    private Thread workerThread = new Thread(new Runnable() {
        public void run() {
            while (!quittingTime) {
                pretendToWork();
            }
            System.out.println("Beer is good");
        }
    });

    public void start() {
        workerThread.start();
    }

    private void pretendToWork() {
        try {
            Thread.sleep(300); // Sleeping on the job?
        } catch (InterruptedException e) {}
    }

    // It's quitting time, wait for workerThread -
    // Called by good boss
    void quit() throws InterruptedException {
        synchronized (quittingTimeLock) {
            quittingTime = true;
            workerThread.join();
        }
    }

    // Rescind quitting time - Called by evil boss
    void keepWorking() {
        synchronized (quittingTimeLock) {
            quittingTime = false;
        }
    }

    public static void main(String[] args)
            throws InterruptedException {
        final BetterWorker worker = new BetterWorker();
        worker.start();
        Timer t = new Timer(true); // Daemon thread
        t.schedule(new TimerTask() {
            public void run() {
                worker.keepWorking();
            }
        }, 500);
        Thread.sleep(400);
        worker.quit();
    }
}
