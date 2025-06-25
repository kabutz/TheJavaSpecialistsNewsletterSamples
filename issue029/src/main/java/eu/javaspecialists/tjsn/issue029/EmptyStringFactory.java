package eu.javaspecialists.tjsn.issue029;

public class EmptyStringFactory implements ObjectFactory {
    public Object makeObject() {
        return new StringBuffer(0).toString();
    }
}
