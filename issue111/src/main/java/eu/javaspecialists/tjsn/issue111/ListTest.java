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

package eu.javaspecialists.tjsn.issue111;

import java.util.*;

public class ListTest {
    private static final int NUM_ELEMENTS = 100 * 1000;

    public static void main(String[] args) {
        List ar = new ArrayList();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            ar.add(i);
        }
        testListBeginning(ar);
        testListBeginning(new LinkedList(ar));
        testListMiddle(ar);
        testListMiddle(new LinkedList(ar));
        testListEnd(ar);
        testListEnd(new LinkedList(ar));
    }

    public static void testListBeginning(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(0, new Object());
            list.remove(0);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("beginning " +
                list.getClass().getSimpleName() + " took " + time);
    }

    public static void testListMiddle(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(NUM_ELEMENTS / 2, new Object());
            list.remove(NUM_ELEMENTS / 2);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("middle    " +
                list.getClass().getSimpleName() + " took " + time);
    }

    public static void testListEnd(List list) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            list.add(new Object());
            list.remove(NUM_ELEMENTS);
        }
        time = System.currentTimeMillis() - time;
        System.out.println("end       " +
                list.getClass().getSimpleName() + " took " + time);
    }
}
