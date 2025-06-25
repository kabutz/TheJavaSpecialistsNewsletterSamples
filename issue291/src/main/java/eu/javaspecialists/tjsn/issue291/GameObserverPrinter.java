package eu.javaspecialists.tjsn.issue291;

public class GameObserverPrinter extends GameObserverSummary {
    private int rolls = 0;

    public void turn() {
        super.turn();
        System.out.println("Turn " + turns() + ":");
    }

    public void roll(int roll) {
        rolls++;
        System.out.println("Rolled a " + roll);
    }

    public void jump(int from, int to) {
        System.out.println("Jumped from " + from + " to " + to);
    }

    public void finished() {
        System.out.println("That took " + turns() + " turns and " +
                rolls + " rolls");
    }
}
