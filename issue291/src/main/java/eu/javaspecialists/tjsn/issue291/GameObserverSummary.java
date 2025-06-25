package eu.javaspecialists.tjsn.issue291;

public class GameObserverSummary implements GameObserver {
    private int turns;

    public void turn() {
        turns++;
    }

    public int turns() {
        return turns;
    }
}
