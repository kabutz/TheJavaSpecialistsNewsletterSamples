package eu.javaspecialists.tjsn.issue133;

import java.util.*;

public abstract class Converter {
    public static final Converter TO_ARRAY = new ToArrayConverter();
    public static final Converter WITH_COPY = new WithCopyConverter();
    public static final Converter WITH_LOOP = new WithLoopConverter();
    public static final Converter UNSAFE = new UnsafeConverter();

    public abstract <T> Collection<T> convert(Class<T> dest,
                                              Collection<?> objects);

    @SuppressWarnings("unchecked")
    public final <T> List<T> convert(Class<T> dest, List<?> objects) {
        return (List<T>) convert(dest, (Collection) objects);
    }
}
