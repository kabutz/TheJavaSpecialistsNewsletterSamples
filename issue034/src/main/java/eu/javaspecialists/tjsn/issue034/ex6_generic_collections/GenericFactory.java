package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

import java.lang.reflect.*;
import java.util.*;

public class GenericFactory {
    public static Collection makeCollection(Collection backing,
                                            Class type) {
        GenericCollection gen = new GenericCollection(backing, type);
        return (Collection) Proxy.newProxyInstance(
                gen.getTypeCollectionClass().getClassLoader(),
                new Class[]{gen.getTypeCollectionClass()},
                gen);
    }

    /* please ignore makeIterator for now ... */
    public static Iterator makeIterator(Iterator backing, Class type) {
        GenericIterator gen = new GenericIterator(backing, type);
        return (Iterator) Proxy.newProxyInstance(
                gen.getTypeIteratorClass().getClassLoader(),
                new Class[]{gen.getTypeIteratorClass()},
                gen);
    }
}
