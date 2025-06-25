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

package eu.javaspecialists.tjsn.issue134;

import java.util.*;

public class TestHarness implements Runnable {
    private static String[] FIRST_NAMES = {"Ted", "Fred", "Andy",
            "Gromit", "Wallace"};
    private static String[] LAST_NAMES = {"Mouse", "Duck", "Pascal",
            "Kabutz", "Monster", "Dread", "Crocket"};
    private StringArray firstNames =
            new StringArray(FIRST_NAMES);
    private StringArray lastNames =
            new StringArray(LAST_NAMES);
    private AllPersons allPersons;
    private int numberOfIterations;

    public TestHarness(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public void init() {
        allPersons = new AllPersons();
        for (int i = 0; i < 250000; i++) {
            allPersons.addPerson(
                    firstNames.nextAsDeepCopy(),
                    lastNames.nextAsDeepCopy());
        }
    }

    public void run() {
        for (int i = 0; i < numberOfIterations; i++) {
            allPersons.findPersonsByName(
                    firstNames.next(),
                    lastNames.next());
        }
    }

    public static void main(String... args) throws InterruptedException {
        ArrayList processes = new ArrayList();
        ArrayList threads = new ArrayList();
        long setup = System.currentTimeMillis();
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            TestHarness harness = new TestHarness(
                    Integer.parseInt(args[1]));
            harness.init();
            processes.add(harness);
        }
        setup = System.currentTimeMillis() - setup;

        System.gc();
        Thread.sleep(1000);
        System.gc();
        Thread.sleep(1000);

        long processing = System.currentTimeMillis();
        for (Iterator it = processes.iterator(); it.hasNext(); ) {
            TestHarness harness = (TestHarness) it.next();
            Thread thread = new Thread(harness);
            thread.start();
            threads.add(thread);
        }
        System.out.println("waiting");
        for (Iterator it = threads.iterator(); it.hasNext(); ) {
            Thread thread = (Thread) it.next();
            thread.join();
        }
        processing = System.currentTimeMillis() - processing;
        System.out.println("Setup time : " + setup);
        System.out.println("Processing time : " + processing);
    }

    public static class StringArray {
        private int nextString = 0;
        private String[] array;

        public StringArray(String[] strings) {
            this.array = strings.clone();
        }

        public String next() {
            String result = array[nextString++];
            nextString %= array.length;
            return result;
        }

        public String nextAsDeepCopy() {
            return new String(next());
        }
    }
}
