package eu.javaspecialists.tjsn.issue154;

import java.util.*;

public class TimerTest {
    public static void main(String... args) {
        Timer timer = new Timer();
        for (int i = 0; i < 5; i++) {
            final int i1 = i;
            timer.schedule(new TimerTask() {
                public void run() {
                    System.out.println("i = " + i1);
                    if (Math.random() < 0.1) {
                        throw new RuntimeException();
                    }
                }
            }, 1000, 1000);
        }
    }
}
