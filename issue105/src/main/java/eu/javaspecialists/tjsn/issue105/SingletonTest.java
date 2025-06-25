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

package eu.javaspecialists.tjsn.issue105;

public class SingletonTest {
    private static final int UPTO = 100 * 1000 * 1000;

    public static void main(String[] args) {
        System.out.print(System.getProperty("java.version") + ",");
        System.out.print(System.getProperty("java.vm.name") + ",");

        long time;

        time = System.currentTimeMillis();
        for (int i = 0; i < UPTO; i++) {
            Singleton1.getInstance();
        }
        time = System.currentTimeMillis() - time;
        System.out.print(time + ",");

        time = System.currentTimeMillis();
        for (int i = 0; i < UPTO; i++) {
            Singleton2.getInstance();
        }
        time = System.currentTimeMillis() - time;
        System.out.print(time + ",");

        time = System.currentTimeMillis();
        for (int i = 0; i < UPTO; i++) {
            Singleton3.getInstance();
        }
        time = System.currentTimeMillis() - time;
        System.out.println(time);
    }
}
