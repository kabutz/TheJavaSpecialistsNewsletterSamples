package eu.javaspecialists.tjsn.issue320;

public class SeveralVirtualThreadsYielding {
    public static void main(String... args) {
        var bench = new TestBench(Thread.ofVirtual(),
                Runtime.getRuntime().availableProcessors(),
                true);
        bench.runExperiment();
    }
}
// allEvenNumbers = 168,633,713
// real    0m10.713s
// user    1m55.022s
// sys     0m0.686s
