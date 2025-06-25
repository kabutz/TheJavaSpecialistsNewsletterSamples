package eu.javaspecialists.tjsn.issue160;

public class DeadlockedOnMonitors implements DeadlockingClass {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() throws InterruptedException {
        synchronized (lock1) {
            Thread.sleep(1000);
            synchronized (lock2) {
            }
        }
    }

    public void method2() throws InterruptedException {
        synchronized (lock2) {
            Thread.sleep(1000);
            synchronized (lock1) {
            }
        }
    }
}