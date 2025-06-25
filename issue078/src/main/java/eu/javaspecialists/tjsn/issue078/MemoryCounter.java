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

package eu.javaspecialists.tjsn.issue078;

import java.lang.reflect.*;
import java.util.*;

/**
 * This class can estimate how much memory an Object uses.  It is
 * fairly accurate for JDK 1.4.2.  It is based on the newsletter #29.
 */
public final class MemoryCounter {
    private static final MemorySizes sizes = new MemorySizes();
    private final Map visited = new IdentityHashMap();
    private final Stack stack = new Stack();

    public synchronized long estimate(Object obj) {
        assert visited.isEmpty();
        assert stack.isEmpty();
        long result = _estimate(obj);
        while (!stack.isEmpty()) {
            result += _estimate(stack.pop());
        }
        visited.clear();
        return result;
    }

    private boolean skipObject(Object obj) {
        if (obj instanceof String) {
            // this will not cause a memory leak since
            // unused interned Strings will be thrown away
            if (obj == ((String) obj).intern()) {
                return true;
            }
        }
        return (obj == null)
                || visited.containsKey(obj);
    }

    private long _estimate(Object obj) {
        if (skipObject(obj)) return 0;
        visited.put(obj, null);
        long result = 0;
        Class clazz = obj.getClass();
        if (clazz.isArray()) {
            return _estimateArray(obj);
        }
        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (!Modifier.isStatic(fields[i].getModifiers())) {
                    if (fields[i].getType().isPrimitive()) {
                        result += sizes.getPrimitiveFieldSize(
                                fields[i].getType());
                    } else {
                        result += sizes.getPointerSize();
                        fields[i].setAccessible(true);
                        try {
                            Object toBeDone = fields[i].get(obj);
                            if (toBeDone != null) {
                                stack.add(toBeDone);
                            }
                        } catch (IllegalAccessException ex) {assert false;}
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        result += sizes.getClassSize();
        return roundUpToNearestEightBytes(result);
    }

    private long roundUpToNearestEightBytes(long result) {
        if ((result % 8) != 0) {
            result += 8 - (result % 8);
        }
        return result;
    }

    protected long _estimateArray(Object obj) {
        long result = 16;
        int length = Array.getLength(obj);
        if (length != 0) {
            Class arrayElementClazz = obj.getClass().getComponentType();
            if (arrayElementClazz.isPrimitive()) {
                result += length *
                        sizes.getPrimitiveArrayElementSize(arrayElementClazz);
            } else {
                for (int i = 0; i < length; i++) {
                    result += sizes.getPointerSize() +
                            _estimate(Array.get(obj, i));
                }
            }
        }
        return result;
    }
}
