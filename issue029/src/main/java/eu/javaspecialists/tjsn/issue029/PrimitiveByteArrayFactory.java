package eu.javaspecialists.tjsn.issue029;

public class PrimitiveByteArrayFactory implements ObjectFactory {
    public Object makeObject() {
        return new byte[1000];
    }
}