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

package eu.javaspecialists.tjsn.issue298;

import java.lang.constant.*;
import java.lang.reflect.*;

public class SealedClassPrinter {
    private static final int TAB = 4;

    public static void main(String... args) {
        printTree(ConstantDesc.class, 0);
    }

    public static void printTree(Class<?> clazz) {
        printTree(clazz, 0);
    }

    private static void printTree(Class<?> clazz, int level) {
        String indent = " ".repeat(level * TAB);
        String modifier = clazz.isSealed() ? "sealed" :
                Modifier.isFinal(clazz.getModifiers()) ? "final" :
                        "non-sealed";
        String name = clazz.getSimpleName();
        System.out.printf("%s%s %s%n", indent, modifier, name);
        var permittedSubclasses = clazz.getPermittedSubclasses();
        if (permittedSubclasses != null) {
            for (var subclass : permittedSubclasses) {
                printTree(subclass, level + 1);
            }
        }
    }
}
