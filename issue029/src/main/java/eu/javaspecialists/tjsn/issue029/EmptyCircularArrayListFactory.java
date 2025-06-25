package eu.javaspecialists.tjsn.issue029;

public class EmptyCircularArrayListFactory implements ObjectFactory {
    public Object makeObject() {
        return new CircularArrayList(0);
    }
}
