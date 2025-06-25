package eu.javaspecialists.tjsn.issue079;

/**
 * This class follows the Null Object Pattern by Bobby Woolf.
 */
public class NullConverter extends ToStringConverter {
    public NullConverter(ToStringConverter successor) {
        super(successor);
    }

    /**
     * This handler is only used if the object is null
     */
    protected boolean isHandler(Object o) {
        return o == null;
    }

    protected StringBuffer toString(Object o, StringBuffer buf) {
        buf.append("(null)");
        return buf;
    }
}
