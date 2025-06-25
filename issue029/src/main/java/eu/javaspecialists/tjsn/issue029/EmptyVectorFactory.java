package eu.javaspecialists.tjsn.issue029;

public class EmptyVectorFactory implements ObjectFactory {
    public Object makeObject() {
        return new java.util.Vector(0);
    }
}
