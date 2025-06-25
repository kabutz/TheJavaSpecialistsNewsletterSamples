package eu.javaspecialists.tjsn.issue320;

public class SinglePlatformThreadsNoYielding {
    public static void main(String... args) {
        var bench = new TestBench(Thread.ofPlatform(), 1, false);
        bench.runExperiment();
    }
}
// allEvenNumbers = 9,393,103,267
// real    0m10.717s
// user    0m11.092s
// sys     0m0.184s
