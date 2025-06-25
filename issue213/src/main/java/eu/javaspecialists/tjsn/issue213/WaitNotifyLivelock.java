package eu.javaspecialists.tjsn.issue213;

public class WaitNotifyLivelock {
    private boolean state = false;
    private final Object lock = new Object();
    public static volatile Thread waitingThread = null;

    public void waitFor() {
        synchronized (lock) {
            waitingThread = Thread.currentThread();
            while (!state) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // In this context, re-interrupting is a mistake
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void notifyIt() {
        synchronized (lock) {
            state = true;
            lock.notifyAll();
        }
    }
}
