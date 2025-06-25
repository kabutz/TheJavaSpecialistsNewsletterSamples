package eu.javaspecialists.tjsn.issue150.take2;

public class MyThread extends Thread {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // do something
        }
    }

    public void shutdown() {
        running = false;
    }
}
