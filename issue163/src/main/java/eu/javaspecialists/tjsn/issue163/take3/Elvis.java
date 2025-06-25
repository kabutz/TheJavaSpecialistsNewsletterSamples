package eu.javaspecialists.tjsn.issue163.take3;

import java.util.*;

public enum Elvis {
    INSTANCE;
    private String[] favoriteSongs =
            {"Hound Dog", "Heartbreak Hotel"};

    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
