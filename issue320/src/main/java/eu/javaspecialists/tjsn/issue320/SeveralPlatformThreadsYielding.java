package eu.javaspecialists.tjsn.issue320;

public class SeveralPlatformThreadsYielding {
    public static void main(String... args) {
        // -XX:+DontYieldALot support removed in Java 24
        var bench = new TestBench(Thread.ofPlatform(),
                Runtime.getRuntime().availableProcessors(),
                true);
        bench.runExperiment();
    }
}
// allEvenNumbers = 92,293,566
// real    0m10.680s
// user    0m16.067s
// sys     1m40.791s
