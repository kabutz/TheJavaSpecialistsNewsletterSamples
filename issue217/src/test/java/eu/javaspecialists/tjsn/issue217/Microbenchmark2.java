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
public class Microbenchmark2 {
    private final NumberRangeAtomicWithPark nrAtomicPark =
            new NumberRangeAtomicWithPark();

    @Benchmark
    public void test_2_1_atomic_park() {
        nrAtomicPark.setUpper(100);
        nrAtomicPark.setLower(10);
        nrAtomicPark.setLower(50);
        nrAtomicPark.setUpper(200);
        nrAtomicPark.setLower(30);
    }
}
