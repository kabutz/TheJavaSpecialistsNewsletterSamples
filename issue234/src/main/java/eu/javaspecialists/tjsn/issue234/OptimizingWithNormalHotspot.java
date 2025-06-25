package eu.javaspecialists.tjsn.issue234;

public class OptimizingWithNormalHotspot {
    public static void main(String... args) {
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    private static void test() {
        long time = System.currentTimeMillis();
        double d = testManyTimes();
        System.out.println("d = " + d);
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }

    private static double testManyTimes() {
        double d = 0;
        for (int i = 0; i < 1_000; i++) {
            d = unrolledTwo(d);
        }
        return d;
    }

    private static double unrolledTwo(double d) {
        for (int j = 0; j < 1_000; j++) {
            d = unrolledOne(d);
        }
        return d;
    }

    private static double unrolledOne(double d) {
        for (int k = 0; k < 1_000; k++) {
            d = updateD(d);
        }
        return d;
    }

    private static double updateD(double d) {
        d += 0;
        return d;
    }
}
