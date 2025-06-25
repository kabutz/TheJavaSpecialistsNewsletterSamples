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

public class StringBufferTest {
    private static final int UPTO = 10 * 1000;
    private final int repeats;

    public StringBufferTest(int repeats) {
        this.repeats = repeats;
    }

    private long testNewBufferDefault() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            StringBuffer buf = new StringBuffer();
            for (int j = 0; j < UPTO; j++) {
                buf.append(j);
            }
            buf.toString();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    private long testNewBufferCorrectSize() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            StringBuffer buf = new StringBuffer(38890);
            for (int j = 0; j < UPTO; j++) {
                buf.append(j);
            }
            buf.toString();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    private long testExistingBuffer() {
        StringBuffer buf = new StringBuffer();
        long time = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            buf.setLength(0);
            for (int j = 0; j < UPTO; j++) {
                buf.append(j);
            }
            buf.toString();
        }
        time = System.currentTimeMillis() - time;
        return time;
    }

    public String testAll() {
        return testNewBufferDefault() + "," +
                testNewBufferCorrectSize() + "," + testExistingBuffer();
    }

    public static void main(String[] args) {
        System.out.print(System.getProperty("java.version") + ",");
        System.out.print(System.getProperty("java.vm.name") + ",");
        // warm up the hotspot compiler
        new StringBufferTest(10).testAll();
        System.out.println(new StringBufferTest(400).testAll());
    }
}
