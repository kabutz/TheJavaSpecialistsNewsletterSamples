package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;


public class GenericsHelper {
    protected String discoverSimpleTypeName(Class type) {
        return type.getName().contains(".") ?
                type.getName().substring(type.getName().lastIndexOf(".") + 1)
                : type.getName();
    }
}
