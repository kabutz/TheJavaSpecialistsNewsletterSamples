package eu.javaspecialists.tjsn.issue133;

import java.util.*;

// warnings caused by the cast
public class UnsafeConverter extends Converter {
    @SuppressWarnings("unchecked")
    public <T> Collection<T> convert(Class<T> dest,
                                     Collection<?> objects) {
        return (Collection<T>) objects;
    }
}
