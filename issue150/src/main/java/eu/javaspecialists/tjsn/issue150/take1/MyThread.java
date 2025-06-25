package eu.javaspecialists.tjsn.issue150.take1;

public class MyThread extends Thread {
    private boolean running = true;

    public void run() {
        while (running) {
            // do something
        }
    }

    public void shutdown() {
        running = false;
    }
}
