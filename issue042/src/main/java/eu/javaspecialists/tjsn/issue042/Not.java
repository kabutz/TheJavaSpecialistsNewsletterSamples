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

package eu.javaspecialists.tjsn.issue042;

public class Not {
    public static void test() {
        boolean flag = true;
        long start;

        start = -System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            flag ^= true;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag ^= true: " + start + "ms");

        start = -System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            flag = !flag;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag = !flag: " + start + "ms");

        start = -System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            flag = flag ? false : true;
        }
        start += System.currentTimeMillis();
        System.out.println("time for flag = flag?false:true: " + start + "ms");
    }

    public static void main(String[] args) throws Exception {
        test();
        Thread.sleep(1);
        test();
    }
}
