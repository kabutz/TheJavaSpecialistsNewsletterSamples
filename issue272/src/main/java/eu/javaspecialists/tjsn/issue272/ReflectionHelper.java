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

package eu.javaspecialists.tjsn.issue272;

import sun.misc.*;

import java.lang.reflect.*;
import java.util.*;

public class ReflectionHelper {
    public static Class<?>[] getAnonymousClasses(Class<?> clazz) {
        Collection<Class<?>> classes = new ArrayList<>();
        for (int i = 1; ; i++) {
            try {
                classes.add(Class.forName(
                        clazz.getName() + "$" + i,
                        false, // do not initialize
                        Thread.currentThread().getContextClassLoader()));
            } catch (ClassNotFoundException e) {
                break;
            }
        }
        for (Class<?> inner : clazz.getDeclaredClasses()) {
            Collections.addAll(classes, getAnonymousClasses(inner));
        }
        for (Class<?> anon : new ArrayList<>(classes)) {
            Collections.addAll(classes, getAnonymousClasses(anon));
        }
        return classes.toArray(new Class<?>[0]);
    }

    private static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Super dangerous and might have unintended side effects.  Use
     * only for testing, and then with circumspection.
     *
     * @param field
     * @param value
     * @throws ReflectiveOperationException
     */
    public static void setStaticFinalField(Field field,
                                           Object value)
            throws ReflectiveOperationException {
        if (!Modifier.isStatic(field.getModifiers())
                || !Modifier.isFinal(field.getModifiers()))
            throw new IllegalArgumentException(
                    "field should be static final");
        Object fieldBase = UNSAFE.staticFieldBase(field);
        long fieldOffset = UNSAFE.staticFieldOffset(field);
        UNSAFE.putObject(fieldBase, fieldOffset, value);
    }

    public static <E extends Enum<E>> E makeEnum(
            Class<E> enumType, String name)
            throws ReflectiveOperationException {
        E e = (E) UNSAFE.allocateInstance(enumType);
        Field nameField = Enum.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(e, name);
        return e;
    }
}
