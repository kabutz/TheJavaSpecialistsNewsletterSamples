package eu.javaspecialists.tjsn.issue199.sleep;

import eu.javaspecialists.tjsn.issue199.dream.*;

// DO NOT CHANGE
public class Sleeper {
    private int level;

    public synchronized int enter(Dream dream) {
        level++;
        try {
            dream.dream(this);
        } finally {
            level--;
        }
        return level;
    }
}
