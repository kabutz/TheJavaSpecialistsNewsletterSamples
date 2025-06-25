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

package eu.javaspecialists.tjsn.issue307;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

public class AccessFlagDemo {
    public static void main(String... args) {
        Arrays.stream(MethodsDemo.class.getDeclaredMethods())
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> System.out.println("""
                        %s:
                            Method: %s
                            Modifiers: %s
                            Modifiers Hex: %s
                            AccessFlags: %s
                        """.formatted(method.getName(),
                        method,
                        Modifier.toString(method.getModifiers()),
                        hexValues(method.getModifiers()),
                        method.accessFlags())));
    }

    private static String hexValues(int modifiers) {
        int bit = 1;
        List<Integer> values = new ArrayList<>();
        while (modifiers != 0) {
            if ((modifiers & bit) != 0) values.add(bit);
            modifiers = modifiers & ~bit;
            bit <<= 1;
        }
        return values.stream()
                .map(val -> String.format("0x%04x", val))
                .collect(Collectors.joining(" ", "[", "]"));
    }
}
