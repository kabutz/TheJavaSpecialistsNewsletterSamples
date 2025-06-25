package eu.javaspecialists.tjsn.issue133;

import java.util.*;

// no warnings generated here
public class WithCopyConverter extends Converter {
    public <T> Collection<T> convert(Class<T> dest,
                                     Collection<?> objects) {
        Collection<T> result = new ArrayList<T>(objects.size());
        for (Object obj : objects) {
            result.add(dest.cast(obj));
        }
        return result;
    }
}
