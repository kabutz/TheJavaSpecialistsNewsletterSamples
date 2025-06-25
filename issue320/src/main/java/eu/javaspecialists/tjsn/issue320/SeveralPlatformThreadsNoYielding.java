package eu.javaspecialists.tjsn.issue320;

public class SeveralPlatformThreadsNoYielding {
    public static void main(String... args) {
        var bench = new TestBench(Thread.ofPlatform(),
                Runtime.getRuntime().availableProcessors(),
                false);
        bench.runExperiment();
    }
}
// allEvenNumbers = 85,245,904,577
// real    0m10.721s
// user    1m54.402s
// sys     0m0.608s
