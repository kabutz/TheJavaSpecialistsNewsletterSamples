package eu.javaspecialists.tjsn.issue019;

import java.awt.*;

public class FrameLookup2 {
    // Singleton Pattern
    private static final FrameLookup2 instance = new FrameLookup2();

    public static FrameLookup2 getInstance() {
        return instance;
    }

    private FrameLookup2() {
    }

    public Frame lookupFrame(String title) {
        Frame[] frames = Frame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].getTitle().equals(title))
                return frames[i];
        }
        return null;
    }
}
