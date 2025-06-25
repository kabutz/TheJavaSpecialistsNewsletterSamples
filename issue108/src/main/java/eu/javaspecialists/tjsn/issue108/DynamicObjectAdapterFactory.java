package eu.javaspecialists.tjsn.issue108;

import java.lang.reflect.*;
import java.util.*;

public class DynamicObjectAdapterFactory {
    public static <T> T adapt(final Object adaptee,
                              final Class<T> target,
                              final Object adapter) {
        return (T) Proxy.newProxyInstance(
                target.getClassLoader(),
                new Class[]{target},
                new InvocationHandler() {
                    private Map<MethodIdentifier, Method> adaptedMethods =
                            new HashMap<MethodIdentifier, Method>();

                    // initializer block - find all methods in adapter object
                    {
                        Method[] methods = adapter.getClass().getDeclaredMethods();
                        for (Method m : methods) {
                            adaptedMethods.put(new MethodIdentifier(m), m);
                        }
                    }

                    public Object invoke(Object proxy, Method method,
                                         Object[] args) throws Throwable {
                        try {
                            Method other = adaptedMethods.get(
                                    new MethodIdentifier(method));
                            if (other != null) {
                                return other.invoke(adapter, args);
                            } else {
                                return method.invoke(adaptee, args);
                            }
                        } catch (InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                    }
                });
    }

    private static class MethodIdentifier {
        private final String name;
        private final Class[] parameters;

        public MethodIdentifier(Method m) {
            name = m.getName();
            parameters = m.getParameterTypes();
        }

        // we can save time by assuming that we only compare against
        // other MethodIdentifier objects
        public boolean equals(Object o) {
            MethodIdentifier mid = (MethodIdentifier) o;
            return name.equals(mid.name) &&
                    Arrays.equals(parameters, mid.parameters);
        }

        public int hashCode() {
            return name.hashCode();
        }
    }
}
