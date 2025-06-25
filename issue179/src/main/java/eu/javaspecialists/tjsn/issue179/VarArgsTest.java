package eu.javaspecialists.tjsn.issue179;

public class VarArgsTest {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        long grandTotal = 0;
        for (int i = 0; i < 100000; i++) {
            grandTotal += test();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time + "ms");
        System.out.println("grandTotal = " + grandTotal);
    }

    private static long test() {
        long total = 0;
        for (int i = 0; i < 10000; i++) {
            total += test(
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                    i, 1, 2, 3
            );
        }
        return total;
    }

    public static int test(int... args) {
        return args[0] + args.length;
    }
}
