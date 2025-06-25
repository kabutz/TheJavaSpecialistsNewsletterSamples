package eu.javaspecialists.tjsn.issue127;

import java.util.*;

public class StavrosLagoon {
    public static void main(String... args) {
        Collection<Object> sea = new ArrayList<Object>();
        sea.add("seaweed");
        sea.add(new Octopus());
        sea.add("rock");
        sea.add("fish");
        sea.add(new Octopus());
        sea.add("sand");
        sea.add(new SeaSlug());
        sea.add("starfish");

        Collection<Alien> aliens =
                CollectionFilter.filter(Alien.class, sea);
        for (Alien alien : aliens) {
            alien.swim();
            alien.glow();
        }
    }
}
