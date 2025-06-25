package eu.javaspecialists.tjsn.issue130;

public class DeadlockingCodeSynchronized implements DeadlockingCode {
    private final Object lock = new Object();

    public synchronized void f() {
        synchronized (lock) {
            // do something
        }
    }

    public void g() {
        synchronized (lock) {
            f();
        }
    }
}
