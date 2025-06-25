package eu.javaspecialists.tjsn.issue193;

public class IntegerObjectFactory implements ObjectFactory {
    public Object makeObject() {
        return new Integer(333);
    }
}