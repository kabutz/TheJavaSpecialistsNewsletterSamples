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

package eu.javaspecialists.tjsn.issue068;

/**
 * Class used to measure the time that a task takes to execute.
 * The method "time" prints out how long it took and returns
 * the time.
 */
public class Timer {
    /**
     * This method runs the Runnable and measures how long it takes
     *
     * @param r is the Runnable for the task that we want to measure
     * @return the time it took to execute this task
     */
    public static long time(Runnable r) {
        long time = -System.currentTimeMillis();
        r.run();
        time += System.currentTimeMillis();
        System.out.println("Took " + time + "ms");
        return time;
    }
}
