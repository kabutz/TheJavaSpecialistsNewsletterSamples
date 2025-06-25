package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

import java.lang.reflect.*;
import java.util.*;

public class GenericCollection extends GenericsHelper implements InvocationHandler {
    private final Collection backing;
    private final Class type;
    private final Class typeCollection;
    private final String typeName;

    public GenericCollection(Collection backing, Class type) {
        this.backing = backing;
        this.type = type;
        this.typeName = discoverSimpleTypeName(type);
        System.out.println("typeName = " + typeName);
        typeCollection = discoverCollection(type);
    }


    /**
     * Find the correct inner class Collection interface.
     *
     * @throws IllegalArgumentException if inner interface Collection
     *                                  not found
     */
    private Class discoverCollection(Class type) {
        Class[] innerClasses = type.getClasses();
        for (int i = 0; i < innerClasses.length; i++) {
            if (innerClasses[i].getName().equals(
                    type.getName() + "$Collection")) {
                return innerClasses[i];
            }
        }
        throw new IllegalArgumentException(
                "Class does not contain inner Collection interface");
    }

    public Class getTypeCollectionClass() {
        return typeCollection;
    }

    /**
     * This is the meat of the GenericCollection.  It finds any
     * method with type name followed by iterator and hijacks it.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (method.getName().equalsIgnoreCase(typeName + "Iterator")) {
            return GenericFactory.makeIterator(backing.iterator(), type);
        }
        return method.invoke(backing, args);
    }
}
