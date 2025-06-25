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
