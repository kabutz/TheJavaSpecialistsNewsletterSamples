package eu.javaspecialists.tjsn.issue225;

import java.util.*;

public final class MyObserverEncapsulated {
    private final Observer observer = new Observer() {
        public void update(Observable o, Object arg) {
            MyObserverEncapsulated.this.update(o, arg);
        }
    };

    private void update(Observable o, Object arg) {
        System.out.println("MyObserver.update");
        System.out.println("o = [" + o + "], arg = [" + arg + "]");
    }

    public void register(Observable observable) {
        observable.addObserver(observer);
    }
}
