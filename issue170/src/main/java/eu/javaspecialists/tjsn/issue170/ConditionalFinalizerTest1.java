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

package eu.javaspecialists.tjsn.issue170;

public class ConditionalFinalizerTest1 {
    public static void main(String[] args)
            throws InterruptedException {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1000 * 1000; i++) {
            ConditionalFinalizer cf = new ConditionalFinalizer(i);
            if (i % (1000 * 1000) != 0) {
                cf.close();
            }
        }
        time = System.currentTimeMillis() - time;
        System.out.println("time = " + time);
    }
}
