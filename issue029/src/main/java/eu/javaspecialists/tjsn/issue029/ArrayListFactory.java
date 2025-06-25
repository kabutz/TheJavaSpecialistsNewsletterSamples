package eu.javaspecialists.tjsn.issue029;

public class ArrayListFactory implements ObjectFactory {
    public Object makeObject() {
        return new java.util.ArrayList();
    }
}
