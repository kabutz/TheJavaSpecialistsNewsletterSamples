package eu.javaspecialists.tjsn.issue206;

import java.util.concurrent.*;

public interface StripedCallable<V> extends Callable<V>,
        StripedObject {
}
