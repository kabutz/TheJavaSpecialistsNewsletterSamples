package eu.javaspecialists.tjsn.issue193;

public class LongObjectFactory implements ObjectFactory {
    public Object makeObject() {
        return new Long(333L);
    }
}