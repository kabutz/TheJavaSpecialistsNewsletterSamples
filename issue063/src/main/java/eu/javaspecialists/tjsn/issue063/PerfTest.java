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

package eu.javaspecialists.tjsn.issue063;

public class PerfTest {
    private final CallDetective cd;

    public PerfTest(CallDetective cd) {
        this.cd = cd;
    }

    public void test() {
        f(5);
        f(10);
        f(20);
    }

    public void f(int depth) {
        // build up a big call stack...
        if (depth > 0) {
            f(depth - 1);
        } else {
            long time = -System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                cd.findCaller(0);
            }
            time += System.currentTimeMillis();
            System.out.println(time + "ms");
        }
    }

    public static void main(String[] args) {
        System.out.println("JDK 1.3 approach to find caller:");
        new PerfTest(new CallDetective1_3()).test();
        System.out.println("JDK 1.4 approach to find caller:");
        new PerfTest(new CallDetective1_4()).test();
        // now let's do it again...
        System.out.println("JDK 1.3 approach to find caller:");
        new PerfTest(new CallDetective1_3()).test();
        System.out.println("JDK 1.4 approach to find caller:");
        new PerfTest(new CallDetective1_4()).test();
    }
}
