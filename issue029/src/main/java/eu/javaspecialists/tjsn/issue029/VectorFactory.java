package eu.javaspecialists.tjsn.issue029;

public class VectorFactory implements ObjectFactory {
    public Object makeObject() {
        return new java.util.Vector(10);
    }
}
