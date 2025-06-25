package eu.javaspecialists.tjsn.issue322;

import java.lang.reflect.*;

/**
 * The proxy() method returns an object that implements the given interface and
 * throws the InterruptedException early for all interruptible methods.
 */
public class EarlyInterruptedExceptionThrower {
    public static <T> T proxy(Class<T> intf, T target) {
        return intf.cast(Proxy.newProxyInstance(
                intf.getClassLoader(), new Class<?>[]{intf},
                (_, method, args) -> { // Unnamed variable _ - JEP 456 Java 22+
                    if (isInterruptible(method) && Thread.interrupted())
                        throw new InterruptedException();
                    try {
                        return method.invoke(target, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause();
                    }
                }));
    }

    private static boolean isInterruptible(Method method) {
        for (Class<?> exceptionType : method.getExceptionTypes()) {
            if (exceptionType == InterruptedException.class) return true;
        }
        return false;
    }
}
