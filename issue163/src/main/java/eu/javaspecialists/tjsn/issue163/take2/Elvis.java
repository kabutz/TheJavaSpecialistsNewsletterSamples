package eu.javaspecialists.tjsn.issue163.take2;

import java.io.*;
import java.util.*;

public class Elvis implements Serializable {
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

    public void setFavouriteSongs(String[] songs) {
        this.songs = songs.clone();
    }

    public void printFavorites() {
        System.out.println(Arrays.toString(songs));
    }

    // this should never be invoked
    private Object readResolve() {
        return INSTANCE;
    }

    private void readObject(ObjectInputStream ex)
            throws IOException {
        throw new InvalidObjectException("Cannot load another Elvis");
    }

    private Object writeReplace() {
        return new ElvisSerializableForm(songs);
    }

    private static class ElvisSerializableForm
            implements Serializable {
        private final String[] songs;

        public ElvisSerializableForm(String[] favoriteSongs) {
            this.songs = favoriteSongs;
        }

        public Object readResolve() {
            Elvis.INSTANCE.setFavouriteSongs(songs);
            return Elvis.INSTANCE;
        }
    }
}
