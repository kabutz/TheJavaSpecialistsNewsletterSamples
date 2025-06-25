package eu.javaspecialists.tjsn.issue129;

public class SuperFastExceptionThrower implements Thrower {
    private static FastException exception = new FastException();

    public void causeException() {
        throw exception;
    }
}
