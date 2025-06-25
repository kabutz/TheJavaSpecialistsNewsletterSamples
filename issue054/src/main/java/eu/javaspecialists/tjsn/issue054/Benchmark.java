package eu.javaspecialists.tjsn.issue054;

/**
 * Benchmark contains some calculation that is executed inside
 * the doCalculation() method a certain number of times.  The
 * number of iterations is returned from the method.
 */
public interface Benchmark {
    int doCalculation();
}
