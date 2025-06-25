package eu.javaspecialists.tjsn.issue129;

public class FastExceptionThrower implements Thrower {
    public void causeException() {
        throw new FastException();
    }
}
