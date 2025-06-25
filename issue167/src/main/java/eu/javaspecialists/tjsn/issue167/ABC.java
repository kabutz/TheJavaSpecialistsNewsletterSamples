package eu.javaspecialists.tjsn.issue167;

import java.lang.reflect.*;

public abstract class ABC {
    public ABC() {
        checkNoArgsConstructor();
    }

    private void checkNoArgsConstructor() {
        Class clazz = getClass();
        try {
            Constructor noargs = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new AssertionError("Class " + clazz.getName() +
                    " needs a no-args constructor");
        }
    }
}