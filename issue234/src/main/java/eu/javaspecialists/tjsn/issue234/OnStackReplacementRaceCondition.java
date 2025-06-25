package eu.javaspecialists.tjsn.issue234;

import java.util.*;

// based on https://stackoverflow.com/questions/32994608/java-8-odd-timing-memory-issue
// java-8-odd-timing-memory-issue
public class OnStackReplacementRaceCondition {
    private static volatile boolean running = true;

    public static void main(String... args) {
        new Timer(true).schedule(new TimerTask() {
            public void run() {
                running = false;
            }
        }, 1000);
        double array[] = new double[1];
        Random r = new Random();
        while (running) {
            double someArray[] = new double[1];
            double someArray2[] = new double[2];

            for (int i = 0; i < someArray2.length; i++) {
                someArray2[i] = r.nextDouble();
            }

            // for whatever reason, using r.nextDouble() here doesn't
            // seem to show the problem, but the # you use doesn't seem
            // to matter either...

            someArray[0] = .45;

            array[0] = 1.0;

            // can use any double here instead of r.nextDouble()
            // or some double arithmetic instead of the new Double
            new Double(r.nextDouble());

            double actual;
            if ((actual = array[0]) != 1.0) {
                System.err.println(
                        "claims array[0] != 1.0....array[0] = " + array[0] +
                                ", was " + actual);

                if (array[0] != 1.0) {
                    System.err.println(
                            "claims array[0] still != 1.0...array[0] = " +
                                    array[0]);
                } else {
                    System.err.println(
                            "claims array[0] now == 1.0...array[0] = " +
                                    array[0]);
                }

                System.exit(1);
            } else if (r.nextBoolean()) {
                array = new double[1];
            }
        }
        System.out.println("All good");
    }
}
