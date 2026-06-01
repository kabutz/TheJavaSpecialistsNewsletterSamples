package eu.javaspecialists.tjsn.issue335;

public class BetterArrayListPuzzle extends BetterListPuzzle {
    public static void main(String... args) {
        new BetterArrayListPuzzle()
                .run(java.util.ArrayList::new);
    }
}