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

package eu.javaspecialists.tjsn.issue273;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class Reflections {
    public static Optional<Method> getTrulyPublicMethod(
            Class<?> clazz, String name, Class<?>... paramTypes) {
        return getTrulyPublicMethods(clazz)
                .stream()
                .filter(method -> matches(method, name, paramTypes))
                .reduce((m1, m2) -> {
                    Class<?> r1 = m1.getReturnType();
                    Class<?> r2 = m2.getReturnType();
                    return r1 != r2 && r1.isAssignableFrom(r2) ? m2 : m1;
                });
    }

    public static Collection<Method> getTrulyPublicMethods(
            Class<?> clazz) {
        Map<String, Method> result = new HashMap<>();
        findTrulyPublicMethods(clazz, result);
        return List.copyOf(result.values());
    }

    private static void findTrulyPublicMethods(
            Class<?> clazz, Map<String, Method> result) {
        if (clazz == null) return;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (isTrulyPublic(method))
                result.putIfAbsent(toString(method), method);
        }
        for (Class<?> intf : clazz.getInterfaces()) {
            findTrulyPublicMethods(intf, result);
        }
        findTrulyPublicMethods(clazz.getSuperclass(), result);
    }

    private static boolean isTrulyPublic(Method method) {
        return Modifier.isPublic(method.getModifiers()
                & method.getDeclaringClass().getModifiers());
    }

    private static String toString(Method method) {
        String prefix = method.getReturnType().getCanonicalName() +
                method.getName() + " (";
        return Stream.of(method.getParameterTypes())
                .map(Class::getCanonicalName)
                .collect(Collectors.joining(", ",
                        prefix, ")"));
    }

    private static boolean matches(
            Method method, String name, Class<?>... paramTypes) {
        return method.getName().equals(name)
                && Arrays.equals(method.getParameterTypes(), paramTypes);
    }
}
