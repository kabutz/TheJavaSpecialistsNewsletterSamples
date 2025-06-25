package eu.javaspecialists.tjsn.issue073;

import java.util.*;

public class LinkedHashMapTest {
    private static void fill(Map players, SportDatabase sd) {
        Player[] p = sd.getPlayers();
        for (int i = 0; i < p.length; i++) {
            players.put(p[i].getKey(), p[i]);
        }
    }

    private static void test(Map players, SportDatabase sd) {
        System.out.println("Testing " + players.getClass().getName());
        fill(players, sd);
        for (Iterator it = players.values().iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        SportDatabase sd = new CricketDatabase();
        test(new HashMap(), sd);
        test(new LinkedHashMap(), sd);
        test(new IdentityHashMap(), sd);
    }
}