package eu.javaspecialists.tjsn.issue068;

/**
 * Class used to measure the time that a task takes to execute.
 * The method "time" prints out how long it took and returns
 * the time.
 */
public class Timer {
    /**
     * This method runs the Runnable and measures how long it takes
     *
     * @param r is the Runnable for the task that we want to measure
     * @return the time it took to execute this task
     */
    public static long time(Runnable r) {
        long time = -System.currentTimeMillis();
        r.run();
        time += System.currentTimeMillis();
        System.out.println("Took " + time + "ms");
        return time;
    }
}
