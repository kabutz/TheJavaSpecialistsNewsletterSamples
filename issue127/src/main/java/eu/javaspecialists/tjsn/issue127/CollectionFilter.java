package eu.javaspecialists.tjsn.issue127;

import java.util.*;

public class CollectionFilter {
    /**
     * Filters the src collection and puts the objects matching the
     * clazz into the dest collection.
     */
    public static <T> void filter(Class<T> clazz,
                                  Collection<?> src,
                                  Collection<T> dest) {
        for (Object o : src) {
            if (clazz.isInstance(o)) {
                dest.add(clazz.cast(o));
            }
        }
    }

    /**
     * Filters the src collection and puts all matching objects into
     * an ArrayList, which is then returned.
     */
    public static <T> Collection<T> filter(Class<T> clazz,
                                           Collection<?> src) {
        Collection<T> result = new ArrayList<T>();
        filter(clazz, src, result);
        return result;
    }
}
