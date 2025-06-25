package eu.javaspecialists.tjsn.issue225;

import java.util.*;

public final class MyObserverMethodReference {
    private final Observer observer = this::update;

    private void update(Observable o, Object arg) {
        System.out.println("MyObserver.update");
        System.out.println("o = [" + o + "], arg = [" + arg + "]");
    }

    public void register(Observable observable) {
        observable.addObserver(observer);
    }
}
