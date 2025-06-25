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

package eu.javaspecialists.tjsn.issue193;

public class MemoryTestBench {
    public long calculateMemoryUsage(ObjectFactory factory) {
        Object handle = factory.makeObject();
        long memory = usedMemory();
        handle = null;
        lotsOfGC();
        memory = usedMemory();
        handle = factory.makeObject();
        lotsOfGC();
        return usedMemory() - memory;
    }

    private long usedMemory() {
        return Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory();
    }

    private void lotsOfGC() {
        for (int i = 0; i < 20; i++) {
            System.gc();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void showMemoryUsage(ObjectFactory factory) {
        long mem = calculateMemoryUsage(factory);
        System.out.println(
                factory.getClass().getSimpleName() + " produced " +
                        factory.makeObject().getClass().getSimpleName() +
                        " which took " + mem + " bytes");
    }
}
