package eu.javaspecialists.tjsn.issue130;

public class ConfusingCode {
    public static long fibonacci(int n) {
        if (n < 2) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void main(String... args)
            throws InterruptedException {
        Thread fibThread = new Thread() {
            public void run() {
                try {
                    for (int i = 37; i < 90; i++) {
                        System.out.println("fib(" + i + ") = " + fibonacci(i));
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        };
        fibThread.start();

        Thread.sleep(10000);
        // fibThread.stop(new NullPointerException("whoops")); // no longer possible
    }
}
