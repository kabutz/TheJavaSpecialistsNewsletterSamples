package eu.javaspecialists.tjsn.issue029;

public class BooleanObjectFactory implements ObjectFactory {
    public Object makeObject() {
        return new Boolean(true);
    }
}
