package eu.javaspecialists.tjsn.issue192;

import java.util.concurrent.atomic.*;

public class EventSource2 extends Thread {
    private final AtomicReference<EventListener> listeners =
            new AtomicReference<EventListener>();

    public void run() {
        while (true) {
            EventListener listener = listeners.getAndSet(null);
            if (listener != null) {
                listener.onEvent(null);
            }
        }
    }

    public void registerListener(EventListener eventListener) {
        listeners.set(eventListener);
    }
}
