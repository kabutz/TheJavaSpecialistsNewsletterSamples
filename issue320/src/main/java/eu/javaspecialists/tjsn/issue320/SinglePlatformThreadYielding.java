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

package eu.javaspecialists.tjsn.issue320;

public class SinglePlatformThreadYielding {
    public static void main(String... args) {
        // -XX:+DontYieldALot support removed in Java 24
        var bench = new TestBench(Thread.ofPlatform(), 1, true);
        bench.runExperiment();
    }
}
// allEvenNumbers = 73,903,324
// real    0m10.692s
// user    0m8.164s
// sys     0m3.235s
