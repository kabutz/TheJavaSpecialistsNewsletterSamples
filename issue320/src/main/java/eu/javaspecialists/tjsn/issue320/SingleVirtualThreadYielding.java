package eu.javaspecialists.tjsn.issue320;

public class SingleVirtualThreadYielding {
    public static void main(String... args) {
        var bench = new TestBench(Thread.ofVirtual(), 1, true);
        bench.runExperiment();
    }
}
// allEvenNumbers = 7,959,055
// real    0m10.677s
// user    0m22.577s
// sys     0m56.556s
