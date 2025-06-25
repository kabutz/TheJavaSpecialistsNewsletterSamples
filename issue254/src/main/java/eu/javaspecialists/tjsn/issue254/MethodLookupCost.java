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

package eu.javaspecialists.tjsn.issue254;

import java.lang.reflect.*;

public class MethodLookupCost {
    public static void main(String... args) throws Exception {
        System.out.println("Warmup");
        for (int i = 0; i < 2; i++) {
            test();
        }
        System.out.println();
        System.out.println("Proper run");
        for (int i = 0; i < 3; i++) {
            test();
        }
        System.out.println(blackhole);
    }

    private static void test() throws Exception {
        for (int methods = 4; methods < 65536; methods *= 5) {
            test(Class.forName("Methods" + methods));
        }
    }

    private static volatile Method blackhole;

    private static void test(Class clazz) throws Exception {
        System.out.println(clazz);
        System.out.println(clazz.toString().replaceAll(".", "-"));
        Method[] methods = clazz.getMethods();

        int firstIndex = fooIndex(methods, 0, 1);
        Method first = methods[firstIndex];
        int lastIndex = fooIndex(methods, methods.length - 1, -1);
        Method last = methods[lastIndex];
        int middleStart = (lastIndex - firstIndex) / 2;
        int middleIndex = fooIndex(methods, middleStart, 1);
        Method middle = methods[middleIndex];

        System.out.println("indexes = (" + firstIndex + ", " +
                middleIndex + ", " + lastIndex + ")");
        System.out.println("foos = (" + first.getName() + ", " +
                middle.getName() + ", " + last.getName() + ")");

        System.out.println("  first  = " + methodFinds(first));
        System.out.println("  middle = " + methodFinds(middle));
        System.out.println("  last   = " + methodFinds(last));
        System.out.println();
    }

    private static int fooIndex(Method[] methods,
                                int start, int inc) {
        int idx = start;
        while (!methods[idx].getName().startsWith("foo")) idx += inc;
        return idx;
    }

    private static long methodFinds(Method method)
            throws Exception {
        return methodFinds(method.getDeclaringClass(),
                method.getName());
    }

    /**
     * Counts how many times we can get the method in a second.
     */
    private static long methodFinds(Class clazz, String method)
            throws Exception {
        long time = System.currentTimeMillis();
        long methodFinds = 0;
        while (System.currentTimeMillis() - time < 1000) {
            blackhole = clazz.getMethod(method, (Class[]) null);
            methodFinds++;
        }
        return methodFinds;
    }
}
