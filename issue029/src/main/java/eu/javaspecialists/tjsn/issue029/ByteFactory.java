package eu.javaspecialists.tjsn.issue029;

public class ByteFactory implements ObjectFactory {
    public Object makeObject() {
        return new Byte((byte) 33);
    }
}