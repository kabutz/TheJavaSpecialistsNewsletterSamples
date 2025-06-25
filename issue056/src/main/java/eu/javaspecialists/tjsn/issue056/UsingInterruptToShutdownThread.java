package eu.javaspecialists.tjsn.issue056;

public class UsingInterruptToShutdownThread extends Thread {
    public void run() {
        while (true) {
            System.out.print(".");
            System.out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt(); // very important
                break;
            }
        }
        System.out.println("Shutting down thread");
    }

    public static void main(String[] args)
            throws InterruptedException {
        Thread t = new UsingInterruptToShutdownThread();
        t.start();
        Thread.sleep(5000);
        t.interrupt();
    }
}
