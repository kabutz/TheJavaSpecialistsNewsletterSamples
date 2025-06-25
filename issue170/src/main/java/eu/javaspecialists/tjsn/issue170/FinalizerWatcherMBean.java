package eu.javaspecialists.tjsn.issue170;

public interface FinalizerWatcherMBean {
    int getNumberOfObjectsThatMightGetFinalizedOneDay();

    void printObjectsThatMightGetFinalizedOneDay(boolean detailed);

    void printUniqueClassesWithFinalizers();

    void runFinalizers();

    void collectGarbage();
}
