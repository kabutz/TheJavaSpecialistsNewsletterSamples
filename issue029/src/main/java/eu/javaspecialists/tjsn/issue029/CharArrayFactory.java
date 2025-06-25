package eu.javaspecialists.tjsn.issue029;

public class CharArrayFactory implements ObjectFactory {
    public Object makeObject() {
        return new char[12];
    }
}
