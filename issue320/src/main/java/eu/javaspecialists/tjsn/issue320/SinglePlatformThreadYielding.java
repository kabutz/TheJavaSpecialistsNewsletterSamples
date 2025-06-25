package eu.javaspecialists.tjsn.issue320;

public class SinglePlatformThreadYielding {
    public static void main(String... args) {
        // -XX:+DontYieldALot support removed in Java 24
        var bench = new TestBench(Thread.ofPlatform(), 1, true);
        bench.runExperiment();
    }
}
// allEvenNumbers = 73,903,324
// real    0m10.692s
// user    0m8.164s
// sys     0m3.235s
