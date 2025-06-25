package eu.javaspecialists.tjsn.issue217;

public interface NumberRange {
    boolean setLower(int newLower);

    boolean setUpper(int newUpper);

    boolean isInRange(int number);

    boolean checkCorrectRange();
}
