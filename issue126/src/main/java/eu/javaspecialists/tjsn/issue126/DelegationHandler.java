package eu.javaspecialists.tjsn.issue126;

import java.lang.reflect.*;

public class DelegationHandler implements InvocationHandler {
    private final Object wrapped;

    public DelegationHandler(Object wrapped) {
        this.wrapped = wrapped;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        System.out.println("Called: " + method);
        return method.invoke(wrapped, args);
    }
}
