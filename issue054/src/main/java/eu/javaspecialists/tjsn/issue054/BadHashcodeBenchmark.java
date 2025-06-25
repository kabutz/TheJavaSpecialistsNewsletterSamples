package eu.javaspecialists.tjsn.issue054;

import java.util.*;

/**
 * The lower-order bits are NOT used as part of this hashcode
 * which is bad if you are using JDK 1.4.0
 */
public class BadHashcodeBenchmark implements Benchmark {
    private static final int ITERATIONS = 1000 * 1000;
    private Object memory;
    private HashMap map = new HashMap(203);
    private Integer[] values;

    public BadHashcodeBenchmark() {
        for (int i = 0; i < 1000; i++) {
            map.put(new Integer(i * 1024), "Value");
        }
        TreeSet keys = new TreeSet(map.keySet());
        values = (Integer[]) keys.toArray(new Integer[1000]);
    }

    public int doCalculation() {
        for (int i = 0; i < ITERATIONS; i++) {
            memory = map.get(values[i % 1000]);
        }
        return ITERATIONS;
    }
}
