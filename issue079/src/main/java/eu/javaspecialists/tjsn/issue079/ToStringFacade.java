package eu.javaspecialists.tjsn.issue079;

/**
 * The default behaviour is to start with the NullConverter,
 * followed by the ArrayConverter and then the ObjectConverter.
 */
public class ToStringFacade {
    private static final ToStringConverter INITIAL =
            new NullConverter(
                    new ArrayConverter(
                            new ObjectConverter(null)));

    public static String toString(Object o) {
        return toString(o, new StringBuffer()).toString();
    }

    public static StringBuffer toString(Object o, StringBuffer buf) {
        return INITIAL.handle(o, buf);
    }
}
