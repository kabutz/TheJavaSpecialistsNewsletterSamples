package eu.javaspecialists.tjsn.issue108;

import java.util.*;

public interface BetterCollection<T> extends Collection<T> {
    T[] toArray();
}
