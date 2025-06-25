package eu.javaspecialists.tjsn.issue130;

import java.util.*;

public class Conference {
    private Collection delegates = new ArrayList();

    public void add(String... names) {
        Collections.addAll(delegates, names);
    }

    public void removeFirst() {
        delegates.remove(0);
    }

    public String toString() {
        return "Conference " + delegates;
    }

    public static void main(String... args) {
        Conference sun_tech_days = new Conference();
        sun_tech_days.add("Herman", "Bobby", "Robert");
        sun_tech_days.removeFirst();
        System.out.println(sun_tech_days);
    }
}
