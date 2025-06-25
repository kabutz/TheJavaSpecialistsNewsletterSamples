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

import java.util.*;

public class MemorySizes {
    private final Map primitiveSizes = new IdentityHashMap() {
        {
            put(boolean.class, new Integer(1));
            put(byte.class, new Integer(1));
            put(char.class, new Integer(2));
            put(short.class, new Integer(2));
            put(int.class, new Integer(4));
            put(float.class, new Integer(4));
            put(double.class, new Integer(8));
            put(long.class, new Integer(8));
        }
    };

    public int getPrimitiveFieldSize(Class clazz) {
        return ((Integer) primitiveSizes.get(clazz)).intValue();
    }

    public int getPrimitiveArrayElementSize(Class clazz) {
        return getPrimitiveFieldSize(clazz);
    }

    public int getPointerSize() {
        return 4;
    }

    public int getClassSize() {
        return 8;
    }
}