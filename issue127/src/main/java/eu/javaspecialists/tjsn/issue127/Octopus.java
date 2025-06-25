package eu.javaspecialists.tjsn.issue127;

import static eu.javaspecialists.tjsn.issue127.Stdio.*;

public class Octopus implements Alien {
    public void swim() {
        printf("Squirting water from my head.\n");
    }

    public void glow() {
        printf("I'll be brown, no, yellow, no green.\n");
    }
}