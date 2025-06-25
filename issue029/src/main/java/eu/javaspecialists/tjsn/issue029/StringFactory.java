package eu.javaspecialists.tjsn.issue029;

public class StringFactory implements ObjectFactory {
    public Object makeObject() {
        StringBuffer buf = new StringBuffer(12);
        buf.append("Hello ");
        buf.append("World!");
        return buf.toString();
    }
}