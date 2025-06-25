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

package eu.javaspecialists.tjsn.issue034.ex6_generic_collections;

import java.lang.reflect.*;
import java.util.*;

public class GenericIterator extends GenericsHelper implements InvocationHandler {
    private final Iterator backing;
    private final Class type;
    private final Class typeIterator;
    private final String typeName;

    public GenericIterator(Iterator backing, Class type) {
        this.backing = backing;
        this.type = type;
        this.typeName = discoverSimpleTypeName(type);
        System.out.println("typeName = " + typeName);

        typeIterator = discoverIterator();
    }

    /**
     * Find the correct inner class Iterator interface.
     *
     * @throws IllegalArgumentException if inner interface Iterator
     *                                  not found
     */
    private Class discoverIterator() {
        Class[] innerClasses = type.getClasses();
        for (int i = 0; i < innerClasses.length; i++) {
            if (innerClasses[i].getName().equals(
                    type.getName() + "$Iterator")) {
                return innerClasses[i];
            }
        }
        throw new IllegalArgumentException(
                "Class does not contain inner Iterator interface");
    }

    public Class getTypeIteratorClass() {
        return typeIterator;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if (method.getName().equalsIgnoreCase("next" + typeName)) {
            return backing.next();
        }
        return method.invoke(backing, args);
    }
}
