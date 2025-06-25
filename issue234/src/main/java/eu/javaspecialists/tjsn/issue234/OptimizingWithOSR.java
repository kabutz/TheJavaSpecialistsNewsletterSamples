package eu.javaspecialists.tjsn.issue234;

public class OptimizingWithOSR {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        double d = testOnce();
        System.out.println("d = " + d);
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }

    private static double testOnce() {
        double d = 0;
        for (int i = 0; i < 1_000_000_000; i++) {
            d += 0;
        }
        return d;
    }
}
