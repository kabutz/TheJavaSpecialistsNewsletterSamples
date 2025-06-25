package eu.javaspecialists.tjsn.issue029;

public class EmptyBooleanArrayFactory implements ObjectFactory {
    public Object makeObject() {
        return new Boolean[0];
    }
}
