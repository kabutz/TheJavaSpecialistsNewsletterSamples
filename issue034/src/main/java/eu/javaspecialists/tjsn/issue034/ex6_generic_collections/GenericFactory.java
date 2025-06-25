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

public class GenericFactory {
    public static Collection makeCollection(Collection backing,
                                            Class type) {
        GenericCollection gen = new GenericCollection(backing, type);
        return (Collection) Proxy.newProxyInstance(
                gen.getTypeCollectionClass().getClassLoader(),
                new Class[]{gen.getTypeCollectionClass()},
                gen);
    }

    /* please ignore makeIterator for now ... */
    public static Iterator makeIterator(Iterator backing, Class type) {
        GenericIterator gen = new GenericIterator(backing, type);
        return (Iterator) Proxy.newProxyInstance(
                gen.getTypeIteratorClass().getClassLoader(),
                new Class[]{gen.getTypeIteratorClass()},
                gen);
    }
}
