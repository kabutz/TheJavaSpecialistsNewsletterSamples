package eu.javaspecialists.tjsn.issue054;

public class EmptyLoopBenchmark implements Benchmark {
    private static final int ITERATIONS = 10 * 1000 * 1000;

    public int doCalculation() {
        for (int i = 0; i < ITERATIONS; i++) {
            ; // NOP
        }
        return ITERATIONS;
    }
}
