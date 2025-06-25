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

public class VisitingIteratorTest {
    public static void main(String[] args) {
        Collection c = new LinkedList();
        for (int i = 0; i < 3; i++) c.add(new Integer(i));
        VisitingIterator vit = new VisitingIterator();

        vit.visit(c, new Object() {
            public void execute(Integer i) {
                System.out.println(i.intValue() + 10);
            }
        });

        System.out.println();

        c.add(new Float(2.1));
        c.add("Hello");

        vit.visit(c, new Object() {
            public void execute(Object o) {
                System.out.println(o);
            }

            public void execute(Number n) {
                System.out.println(n.intValue() + 20);
            }

            public void execute(Integer i) {
                System.out.println(i.intValue() + 10);
            }

            public void execute(String s) {
                System.out.println(s.toLowerCase());
            }
        });

        System.out.println();

        vit.visit(c, new Object() {
            public void execute(Integer i) {
                System.out.println(i.intValue() + 10);
            }
        });
    }
}
