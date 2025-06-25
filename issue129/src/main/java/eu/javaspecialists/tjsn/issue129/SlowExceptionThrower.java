package eu.javaspecialists.tjsn.issue129;

public class SlowExceptionThrower implements Thrower {
    public void causeException() throws Exception {
        throw new Exception();
    }
}
