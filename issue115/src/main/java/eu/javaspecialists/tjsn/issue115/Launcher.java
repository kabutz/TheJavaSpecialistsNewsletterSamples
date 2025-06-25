package eu.javaspecialists.tjsn.issue115;

import java.util.*;

public class Launcher {
    public static void main(String[] args) throws Exception {
        String testName = args[0];
        final CreateTest job =
                (CreateTest) Class.forName(testName).newInstance();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println(job.getCount());
                System.exit(0);
            }
        }, 10_000);
        job.run();
    }
}