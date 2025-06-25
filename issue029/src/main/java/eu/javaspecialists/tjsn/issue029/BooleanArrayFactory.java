package eu.javaspecialists.tjsn.issue029;

public class BooleanArrayFactory implements ObjectFactory {
    public Object makeObject() {
        Boolean[] objs = new Boolean[1000];
        for (int i = 0; i < objs.length; i++)
            objs[i] = new Boolean(true);
        return objs;
    }
}