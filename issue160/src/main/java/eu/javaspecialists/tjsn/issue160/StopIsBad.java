package eu.javaspecialists.tjsn.issue160;

public class StopIsBad {
    private static final boolean USE_INTERRUPT = false;
    public static final Object lock = new Object();
    public static int x = 5;
    public static int y = 5;

    // invariant: x + y = 10
    static class Foo extends Thread {
        public void run() {
            while (!isInterrupted()) {
                synchronized (lock) {
                    x++;
                    sleepQuietly(); // make sure stop() happens here
                    y--;
                }
            }
        }

        private void sleepQuietly() {
            try {
                sleep(10);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String... args)
            throws InterruptedException {
        while (true) {
            System.out.println("Creating new foo");
            Foo foo = new Foo();
            foo.start();
            Thread.sleep(50);
            if (USE_INTERRUPT) {
                foo.interrupt();
            } else {
                foo.stop();
            }
            synchronized (lock) {
                if (x + y != 10) {
                    throw new IllegalStateException(
                            "Invariant is broken: " + (x + y));
                }
            }
            foo.join();
        }
    }
}
