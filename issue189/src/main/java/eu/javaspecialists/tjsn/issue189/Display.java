package eu.javaspecialists.tjsn.issue189;

import lejos.nxt.*;

public class Display {
    public void show(String message) {
        LCD.clear();
        System.out.println(message);
    }
}
