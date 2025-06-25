package eu.javaspecialists.tjsn.issue236;

public class FibonacciTest {
    public static void main(String... args) {
        Fibonacci fib = new Fibonacci();

        for (int i = 0; i < 10; i++) {
            if (!fib.f(i).equals(fib.f_slow(i))) {
                throw new AssertionError("Mismatch at i=" + i);
            }
        }

        for (int n = 10000; n < 50_000_000; n *= 2) {
            timeTest(fib, n);
        }
    }

    private static void timeTest(Fibonacci fib, int n) {
        System.out.printf("fib(%,d)%n", n);
        long time = System.currentTimeMillis();
        System.out.println(fib.f(n).bitLength());
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
