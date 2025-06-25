package eu.javaspecialists.tjsn.issue307;

public class MethodsDemo implements Comparable<MethodsDemo> {
    private static final synchronized strictfp void foo() {
    }

    void varArgsMethod(Object... args) {
    }

    // bridge (volatile) & synthetic
    public int compareTo(MethodsDemo o1) {
        return 0;
    }
}
