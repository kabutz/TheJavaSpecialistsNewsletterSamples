package eu.javaspecialists.tjsn.issue163.take1;

import java.util.*;

public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private volatile String[] songs =
            {"Hound Dog", "Heartbreak Hotel"};

    public static Elvis getInstance() {
        return INSTANCE;
    }

    private Elvis() {
    }

    public void leaveTheBuilding() {
    }

    public void printFavorites() {
        System.out.println(Arrays.toString(songs));
    }

    public void setFavouriteSongs(String[] songs) {
        this.songs = songs.clone();
    }
}
