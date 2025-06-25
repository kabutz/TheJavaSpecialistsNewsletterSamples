package eu.javaspecialists.tjsn.issue168.util;

public class DelegationException extends RuntimeException {
    public DelegationException(String message) {
        super(message);
    }

    public DelegationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DelegationException(Throwable cause) {
        super(cause);
    }
}