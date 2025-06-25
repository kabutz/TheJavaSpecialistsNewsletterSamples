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

public class FastJavaFast {
    public void test() {
        for (int i = 0; i < 1000 * 1000 * 1000; i++) {
            test2();
        }
    }

    void test2() {
        for (int j = 0; j < 1000 * 1000 * 1000; j++) {
            test3();
        }
    }


    void test3() {
        for (int k = 0; k < 1000 * 1000 * 1000; k++) {
            foo();
        }
    }

    // this is a "volatile" method, in the "C" sense
    public void foo() {
    }

    public static void main(String[] args) throws Exception {
        FastJavaFast fast = new FastJavaFast() {
            // I am overriding the foo() method
            public void foo() {
                // do nothing
            }
        };

        // Running the benchmark
        long time = System.currentTimeMillis();
        fast.foo();
        fast.test3();
        fast.test2();
        fast.test();
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
