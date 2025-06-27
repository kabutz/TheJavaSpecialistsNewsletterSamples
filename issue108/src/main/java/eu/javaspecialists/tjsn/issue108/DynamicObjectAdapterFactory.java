/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
