package eu.javaspecialists.tjsn.issue054;

public class PerformanceTest {
    private static final int REPEATS = 10;

    public static void main(String[] args) {
        if (args.length != 1) {
            usage();
        }
        System.out.println(
                "JVM version:" + System.getProperty("java.version"));
        try {
            evaluate((Benchmark) Class.forName(args[0]).newInstance());
            System.out.println();
        } catch (Exception ex) {
            ex.printStackTrace();
            usage();
        }
    }

    private static void usage() {
        System.err.println(
                "usage: java PerformanceTest BenchmarkClass");
        System.err.println(
                "\tBenchmarkClass is a class implementing Benchmark");
        System.exit(1);
    }

    private static void evaluate(Benchmark benchmark) {
        // do the function once to "warm up" HotSpot compiler
        benchmark.doCalculation();
        long average = 0;
        for (int i = 0; i < REPEATS; i++) {
            long time = -System.currentTimeMillis();
            int iterations = benchmark.doCalculation();
            time += System.currentTimeMillis();
            System.out.print(iterations / time);
            System.out.print("  ");
            System.out.flush();
            average += iterations / time;
        }
        System.out.println();
        System.out.println(
                "Average "
                        + (average / REPEATS)
                        + " iterations per millisecond");
    }
}
