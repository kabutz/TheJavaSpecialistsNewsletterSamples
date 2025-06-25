package eu.javaspecialists.tjsn.issue199.sleep;

import eu.javaspecialists.tjsn.issue199.dream.*;

// DO NOT CHANGE
public class Main {
    public static void main(String... args) {
        if (new Sleeper().enter(new Dream()) != 0) {
            // The goal is to reach this line
            System.out.println("Am I still dreaming?");
        }
    }
}
