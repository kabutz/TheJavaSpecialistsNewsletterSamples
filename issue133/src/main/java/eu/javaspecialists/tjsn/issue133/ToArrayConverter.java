package eu.javaspecialists.tjsn.issue133;

import java.lang.reflect.*;
import java.util.*;

// warnings caused by the casts
public class ToArrayConverter extends Converter {
    @SuppressWarnings("unchecked")
    public <T> Collection<T> convert(Class<T> dest,
                                     Collection<?> objects) {
        try {
            objects.toArray((T[]) Array.newInstance(dest, objects.size()));
        } catch (ArrayStoreException ase) {
            throw new ClassCastException();
        }
        return (Collection<T>) objects;
    }
}
