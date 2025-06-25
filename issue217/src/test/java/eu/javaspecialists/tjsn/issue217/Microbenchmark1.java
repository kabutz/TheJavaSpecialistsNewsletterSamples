package eu.javaspecialists.tjsn.issue217;

import org.openjdk.jmh.annotations.*;

import static java.util.concurrent.TimeUnit.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = SECONDS)
@Fork(5)
@State(Scope.Benchmark)
@Threads(8)
public class Microbenchmark1 {
    private final NumberRangeSynchronized nrSync =
            new NumberRangeSynchronized();
    private final NumberRangeAtomic nrAtomic =
            new NumberRangeAtomic();

    @Benchmark
    public void test_1_1_synchronized() {
        nrSync.setUpper(100);
        nrSync.setLower(10);
        nrSync.setLower(50);
        nrSync.setUpper(200);
        nrSync.setLower(30);
    }

    @Benchmark
    public void test_1_2_atomic() {
        nrAtomic.setUpper(100);
        nrAtomic.setLower(10);
        nrAtomic.setLower(50);
        nrAtomic.setUpper(200);
        nrAtomic.setLower(30);
    }
}
