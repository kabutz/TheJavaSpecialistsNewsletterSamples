package eu.javaspecialists.tjsn.issue129;

public class FastException extends RuntimeException {
    public Throwable fillInStackTrace() {
        return null;
    }
}
