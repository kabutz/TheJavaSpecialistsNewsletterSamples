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

package eu.javaspecialists.tjsn.issue158;

import java.lang.reflect.*;
import java.util.*;

public class PolymorphismCliffRandomTest {
    private static final int UPTO = 100 * 1000 * 1000;

    public static void main(String[] args) throws Exception {
        int offset = Integer.parseInt(args[0]);

        Test[] tests = generateTestData(offset);

        printDescriptions(tests);

        System.out.println("Warmup");
        test_all(tests);

        System.out.println("Actual run");
        printHeader(tests);

        test_all(tests);
    }

    private static void test_all(Test[] tests) {
        for (int j = 0; j < 10; j++) {
            run_tests(tests);
        }
    }

    private static void printDescriptions(Test[] tests) {
        System.out.println(tests[0].getClass().getSimpleName() +
                ": " + tests[0].description());
        System.out.println();
    }

    public static void run_tests(Test[] tests) {
        long time = System.currentTimeMillis();
        test(tests);
        time = System.currentTimeMillis() - time;
        System.out.print(time + "\t");
        System.out.flush();
        System.out.println();
    }

    public static void test(Test[] sources) {
        Test t0 = makeRandomTest(sources);
        Test t1 = makeRandomTest(sources);
        Test t2 = makeRandomTest(sources);
        Test t3 = makeRandomTest(sources);
        Test t4 = makeRandomTest(sources);
        Test t5 = makeRandomTest(sources);
        Test t6 = makeRandomTest(sources);
        Test t7 = makeRandomTest(sources);
        Test t8 = makeRandomTest(sources);
        Test t9 = makeRandomTest(sources);
        for (int i = 0; i < UPTO / 10; i++) {
            t0.run();
            t1.run();
            t2.run();
            t3.run();
            t4.run();
            t5.run();
            t6.run();
            t7.run();
            t8.run();
            t9.run();
        }
    }

    private static Test makeRandomTest(Test[] sources) {
        return sources[((int) (Math.random() * sources.length))];
    }

    private static void printHeader(Test[] tests) {
        System.out.print(tests[0].getClass().getSimpleName());
        System.out.print('\t');
        System.out.println();
    }

    private static Test[] generateTestData(int offset)
            throws Exception {
        switch (offset) {
            default:
                throw new IllegalArgumentException("offset:" + offset);
            case 0:
                return fillSources(A1.class, B1.class, B1.class);
            case 1:
                return fillSources(A2.class, B2.class, C2.class);
            case 2:
                return fillSources(
                        A3.class, B3.class, C3.class, D3.class);
            case 3:
                return fillSources(
                        A4.class, B4.class, C4.class, D4.class, E4.class);
            case 4:
                return fillSources(
                        A5.class, B5.class, C5.class, D5.class, E5.class,
                        F5.class);
            case 5:
                return fillSources(
                        A6.class, B6.class, C6.class, D6.class, E6.class,
                        F6.class, G6.class, I6.class, J6.class);
        }
    }

    private static Test[] fillSources(
            Class<? extends Test> aClass,
            Class<?> bClass, Class<?>... bClasses)
            throws Exception {
        Test[] sources = new Test[1000];
        Random rand = new Random(0);
        Constructor<? extends Test> constr =
                aClass.getConstructor(bClass);
        for (int i = 0; i < sources.length; i++) {
            int offset = Math.abs(rand.nextInt() % bClasses.length);
            Object b = bClasses[offset].newInstance();
            sources[i] = constr.newInstance(b);
        }
        return sources;
    }
}
