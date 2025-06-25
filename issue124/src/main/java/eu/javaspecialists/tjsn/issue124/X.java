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

package eu.javaspecialists.tjsn.issue124;

import java.util.*;

public class X {
    private final byte[] byteValue = new byte[16];

    X() {
        new Random(0).nextBytes(byteValue);
    }

    public byte[] testClone() {
        return byteValue.clone();
    }

    public byte[] testNewAndCopy() {
        byte[] b = new byte[byteValue.length];
        System.arraycopy(byteValue, 0, b, 0, byteValue.length);
        return b;
    }

    public static void main(String... args) {
        doTest();
        doTest();
    }

    private static void doTest() {
        X x = new X();
        int m = 50000000;

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < m; i++) {
            x.testClone();
        }
        long t1 = System.currentTimeMillis();

        System.out.println("clone(): " + (t1 - t0));

        t0 = System.currentTimeMillis();
        for (int i = 0; i < m; i++) {
            x.testNewAndCopy();
        }
        t1 = System.currentTimeMillis();

        System.out.println("arraycopy(): " + (t1 - t0));
    }
}
