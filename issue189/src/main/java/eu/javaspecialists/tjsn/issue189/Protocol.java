package eu.javaspecialists.tjsn.issue189;

public interface Protocol {
    int STOP = 0;
    int RIGHT = Integer.MAX_VALUE;
    int LEFT = Integer.MIN_VALUE;
    int EXIT = Integer.MAX_VALUE - 1;
    int BLUE = Integer.MAX_VALUE - 2;
    int RED = Integer.MAX_VALUE - 3;
    int GREEN = Integer.MAX_VALUE - 4;
    int NO_LIGHT = Integer.MAX_VALUE - 5;
    // any other positive value is the forward speed and a
// negative value is the reverse speed
}
