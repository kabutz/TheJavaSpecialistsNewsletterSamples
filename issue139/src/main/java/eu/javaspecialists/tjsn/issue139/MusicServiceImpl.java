package eu.javaspecialists.tjsn.issue139;

import java.util.*;

public class MusicServiceImpl implements MusicService {
    private final String[] titles = {
            "Don't Worry Be Happy - Bobby Mcferrin",
            "I've just seen Jesus - Larnelle Harris",
            "When Praise Demands a Sacrifice - Larnelle Harris",
            "Sultans of Swing - Dire Straits"
    };

    public List<String> getTitleList() {
        return Arrays.asList(titles);
    }

    public void play(String title) {
        System.out.println("Playing: " + title);
    }
}
