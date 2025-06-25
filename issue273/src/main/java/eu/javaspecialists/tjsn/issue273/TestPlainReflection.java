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

package eu.javaspecialists.tjsn.issue273;

import eu.javaspecialists.tjsn.issue273.problem.*;
import eu.javaspecialists.tjsn.issue273.problem.inner.*;

import java.lang.reflect.*;
import java.util.stream.*;

public class TestPlainReflection {
    public static void main(String... args) {
        System.out.println("Testing private inner class");
        test(Hidden.getPrivateInnerClass());
        System.out.println();

        System.out.println("Testing method inner class");
        test(Hidden.getMethodClass());
        System.out.println();

        System.out.println("Testing lambda");
        test(Hidden.getLambda());
    }

    private static void test(A a) {
        Stream.of(a.getClass().getMethods())
                .forEach(System.out::println);
        printMethodResult(a, "foo");
        printMethodResult(a, "bar");
    }

    private static void printMethodResult(Object o, String name) {
        try {
            Method method = o.getClass().getMethod(name);
            System.out.println(method.invoke(o));
        } catch (NoSuchMethodException e) {
            System.out.println("Method " + name + "() not found");
        } catch (IllegalAccessException e) {
            System.out.println("Illegal to call " + name + "()");
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}
