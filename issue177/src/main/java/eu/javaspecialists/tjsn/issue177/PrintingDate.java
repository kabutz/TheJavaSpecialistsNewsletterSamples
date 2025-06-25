package eu.javaspecialists.tjsn.issue177;

import java.util.*;

public class PrintingDate {
    public static void main(String... args) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            System.out.println(new Date() + " log message");
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
