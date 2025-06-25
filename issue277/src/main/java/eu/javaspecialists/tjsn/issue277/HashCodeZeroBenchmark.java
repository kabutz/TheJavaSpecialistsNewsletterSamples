package eu.javaspecialists.tjsn.issue277;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.*;

@Fork(value = 3)
@Warmup(iterations = 5, time = 3)
@Measurement(iterations = 10, time = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class HashCodeZeroBenchmark {
    private static final String ZERO_HASH = "ARcZguv";
    @Param({"-1", "0", "1", "2", "3", "4", "5"})
    private int powerOfTen;
    private String s;

    @Setup
    public void setup() {
        int chunks = (int) Math.pow(10, powerOfTen);
        StringBuilder sb = new StringBuilder(
                ZERO_HASH.length() * chunks
        );
        for (int i = 0; i < chunks; i++) {
            sb.append(ZERO_HASH);
        }
        s = sb.toString();
    }

    @Benchmark
    public int hashCodeString() {
        return s.hashCode();
    }
}
