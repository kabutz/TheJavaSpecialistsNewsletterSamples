package eu.javaspecialists.tjsn.issue054;

public class AdditionBenchmark implements Benchmark {
    private static final int ITERATIONS = 10 * 1000 * 1000;
    private int memory;

    public int doCalculation() {
        int val = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            val = val + i;
        }
        memory = val;
        return ITERATIONS;
    }
}
