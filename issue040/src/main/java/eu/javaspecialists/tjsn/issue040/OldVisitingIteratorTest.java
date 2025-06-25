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

package eu.javaspecialists.tjsn.issue040;

import java.util.*;

public class OldVisitingIteratorTest {
    public static void main(String[] args) {
        Collection c = new LinkedList();
        for (int i = 0; i < 3; i++) c.add(new Integer(i));

        Iterator it = c.iterator();
        while (it.hasNext()) {
            // lots of brackets - looks almost like Lisp - argh
            System.out.println(((Integer) it.next()).intValue() + 10);
        }

        c.add(new Float(2.1));
        c.add("Hello");

        it = c.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof Integer) {
                System.out.println(((Integer) o).intValue() + 10);
            } else if (o instanceof Number) {
                System.out.println(((Number) o).intValue() + 20);
            } else if (o instanceof String) {
                System.out.println(((String) o).toLowerCase());
            } else {
                System.out.println(o);
            }
        }

        it = c.iterator();
        while (it.hasNext()) {
            System.out.println(((Integer) it.next()).intValue() + 10);
        }
    }
}
