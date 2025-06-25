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

package eu.javaspecialists.tjsn.issue312;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class StockNameTest {
    @Test
    public void testStockName() {
        var sn = new StockName("HUGE",
                "Huge Fruit, Inc.", "Fruit Titanesque, Inc.");

        test("HUGE - Huge Fruit, Inc.", "%s", sn.toString());
        test("Huge Fruit, Inc.", "%s", sn);
        test("HUGE FRUIT, INC.", "%S", sn);
        test("HUGE", "%#s", sn);
        test("HUGE      ", "%-10.8s", sn);
        test("      HUGE", "%10.8s", sn);
        test("          ", "%10.0s", sn);
        test("HU*", "%.3s", sn);
        test("Huge Fruit,*", "%.12s", sn);
        test("   Fruit Titanesque, Inc.", Locale.FRANCE, "%25s", sn);
        test("HUGE", Locale.FRANCE, "%#s", sn);
    }

    private static void test(String expected, String format,
                             Object arg) {
        test(expected, Locale.US, format, arg);
    }

    private static void test(String expected, Locale locale,
                             String format, Object arg) {
        var formatter = new Formatter();
        var formatted = formatter.format(locale, format, arg);
        assertEquals(expected, formatted.toString());
        System.out.println("\"" + formatted + "\"");
    }
}
