package eu.javaspecialists.tjsn.issue133;

import java.util.*;

public class WithLoopConverter extends Converter {
    @SuppressWarnings("unchecked")
    public <T> Collection<T> convert(Class<T> dest,
                                     Collection<?> objects) {
        for (Object obj : objects) {
            if (obj != null && !dest.isInstance(obj)) {
                throw new ClassCastException();
            }
        }
        return (Collection<T>) objects; // this causes the warning
    }
}
