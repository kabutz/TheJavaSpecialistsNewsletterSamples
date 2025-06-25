package eu.javaspecialists.tjsn.issue291;

public interface GameObserver {
    default void turn() {
    }

    default void roll(int roll) {
    }

    default void jump(int from, int to) {
    }

    default void finished() {
    }
}
