package eu.javaspecialists.tjsn.issue029;

public class BooleanFlyweightFactory implements ObjectFactory {
    public Object makeObject() {
        Boolean[] objs = new Boolean[1000];
        for (int i = 0; i < objs.length; i++)
            objs[i] = Boolean.TRUE;
        return objs;
    }
}
