package eu.javaspecialists.tjsn.issue063;

/**
 * This interface is used to determine who called us.
 * The implementation does not have to be thread-safe.
 */
public interface CallDetective {
    /**
     * Returns a String representation of who called us,
     * going back depth levels.
     *
     * @param depth must be greater than 0 and may not
     *              exceed the call stack depth.
     */
    public String findCaller(int depth);

    public class Factory {
        public static CallDetective makeCallDetective() {
            if ("1.4".compareTo(System.getProperty("java.version")) > 0) {
                return new CallDetective1_3();
            } else {
                return new CallDetective1_4();
            }
        }
    }
}
