package eu.javaspecialists.tjsn.issue029;

public class IntegerToStringFactory implements ObjectFactory {
    public Object makeObject() {
        return "" + Integer.toString(20);
    }
}
