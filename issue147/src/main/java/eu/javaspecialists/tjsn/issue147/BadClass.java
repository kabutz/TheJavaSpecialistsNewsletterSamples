package eu.javaspecialists.tjsn.issue147;

public class BadClass extends Thread {
    private final Object lock1;
    private final Object lock2;

    public BadClass(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    public void run() {
        while (true) {
            synchronized (lock1) {
                synchronized (lock2) {
                    System.out.print('.');
                    System.out.flush();
                }
            }
        }
    }

    public static void main(String... args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        BadClass bc1 = new BadClass(lock1, lock2);
        BadClass bc2 = new BadClass(lock2, lock1);
        bc1.start();
        bc2.start();
    }
}
