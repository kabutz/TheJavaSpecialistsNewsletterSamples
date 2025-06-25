package eu.javaspecialists.tjsn.issue063;

/**
 * This class is used to determine who called us.
 * It is deliberately not thread-safe.
 */
public class CallDetective1_4 implements CallDetective {
    private final Throwable tracer = new Throwable();

    public String findCaller(int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException();
        }
        tracer.fillInStackTrace();
        return tracer.getStackTrace()[depth + 1].toString();
    }
}
