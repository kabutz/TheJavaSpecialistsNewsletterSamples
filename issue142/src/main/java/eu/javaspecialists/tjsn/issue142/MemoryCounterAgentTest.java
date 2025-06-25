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

package eu.javaspecialists.tjsn.issue142;

import java.util.*;
import java.util.concurrent.locks.*;

public class MemoryCounterAgentTest {
    public static void measureSize(Object o) {
        long memShallow = MemoryCounterAgent.sizeOf(o);
        long memDeep = MemoryCounterAgent.deepSizeOf(o);
        System.out.printf("%s, shallow=%d, deep=%d%n",
                o.getClass().getSimpleName(),
                memShallow, memDeep);
    }

    public static void main(String... args) {
        measureSize(new Object());
        measureSize(new HashMap());
        measureSize(new LinkedHashMap());
        measureSize(new ReentrantReadWriteLock());
        measureSize(new byte[1000]);
        measureSize(new boolean[1000]);
        measureSize(new String("Hello World".toCharArray()));
        measureSize("Hello World");
        measureSize(10);
        measureSize(100);
        measureSize(1000);
        measureSize(new Parent());
        measureSize(new Kid());
        measureSize(Thread.State.TERMINATED);
    }

    private static class Parent {
        private int i;
        private boolean b;
        private long l;
    }

    private static class Kid extends Parent {
        private boolean b;
        private float f;
    }
}
