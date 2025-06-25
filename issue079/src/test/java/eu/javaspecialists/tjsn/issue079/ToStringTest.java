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

package eu.javaspecialists.tjsn.issue079;

import junit.framework.*;

import java.util.*;

// Java 9+, use --add-opens java.base/java.lang=ALL-UNNAMED
// --add-opens java.base/java.util=ALL-UNNAMED
public class ToStringTest extends TestCase {
    public void testPackageStripping() {
        assertEquals("Integer",
                Utils.stripPackageName(Integer.class));
        assertEquals("Map.Entry",
                Utils.stripPackageName(Map.Entry.class));
        assertEquals("ToStringTest",
                Utils.stripPackageName(ToStringTest.class));
    }

    public void testNull() {
        assertEquals("(null)", ToStringFacade.toString(null));
    }

    public void testInteger() {
        assertEquals("(Integer:value=42)",
                ToStringFacade.toString(new Integer(42)));
    }

    public void testString() {
        // valid for Java 6
        // assertEquals("(String:value=(char[]={H,e,l,l,o, ,W,o,r,l,d,!}), " +
        //                 "offset=0, count=12, hash=0)",
        //         ToStringFacade.toString("Hello World!"));
        // Java 9+ needs the following:
        // --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED
        assertEquals("(String:value=(byte[]={72,101,108,108,111,32,87,111,114,108,100,33}), " +
                        "coder=0, hash=0, hashIsZero=false)",
                ToStringFacade.toString("Hello World!"));
    }

    public void testArray() {
        assertEquals("(int[]={1,2,3})",
                ToStringFacade.toString(new int[]{1, 2, 3}));
    }

    public void testMultiArray() {
        assertEquals("(long[][][][]={{{{1,2,3},{4}},{{5}}}})",
                ToStringFacade.toString(
                        new long[][][][]{{{{1, 2, 3}, {4}}, {{5}}}}));
    }

    public void testTestClass() {
        assertEquals("(ToStringTest.TestClass:names=" +
                        "(String[]={\"Heinz\",\"Joern\",\"Pieter\",\"Herman\"" +
                        ",\"John\"}), totalIQ=900)",
                ToStringFacade.toString(new TestClass()));
    }

    private static class TestClass {
        private final String[] names = {"Heinz", "Joern", "Pieter",
                "Herman", "John"};
        private final int totalIQ = 180 * 5;
    }

    public void testArrayList() {
        ArrayList list = new ArrayList();
        list.add("Heinz");
        list.add("Helene");
        list.add("Maxi");
        list.add("Connie");
        assertEquals("(ArrayList:elementData=(Object[]={\"Heinz\"" +
                        ",\"Helene\",\"Maxi\",\"Connie\",(null),(null),(null)," +
                        "(null),(null),(null)}), size=4, AbstractList.modCount=4)",
                ToStringFacade.toString(list));
    }
}
