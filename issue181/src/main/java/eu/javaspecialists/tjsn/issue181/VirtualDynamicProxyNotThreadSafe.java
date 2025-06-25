package eu.javaspecialists.tjsn.issue181;

import java.lang.reflect.*;

public class VirtualDynamicProxyNotThreadSafe
        implements InvocationHandler {
    private final Class realSubjectClass;
    private Object realSubject;

    public VirtualDynamicProxyNotThreadSafe(Class realSubjectClass) {
        this.realSubjectClass = realSubjectClass;
    }

    private Object realSubject() throws Exception {
        if (realSubject == null) {
            realSubject = realSubjectClass.newInstance();
        }
        return realSubject;
    }

    public Object invoke(
            Object proxy, Method method, Object[] args)
            throws Throwable {
        return method.invoke(realSubject(), args);
    }
}
