package eu.javaspecialists.tjsn.issue291;

import java.util.*;

public class GameObserverDetailed extends GameObserverSummary {
    private final List<Integer> rollHistory = new ArrayList<>();
    private int climbs, slides;

    public List<Integer> rollHistory() {
        return List.copyOf(rollHistory);
    }

    public void roll(int roll) {
        rollHistory.add(roll);
    }

    public void jump(int from, int to) {
        if (from < to) climbs += to - from;
        else slides += from - to;
    }

    public int climbs() {
        return climbs;
    }

    public int slides() {
        return slides;
    }
}
