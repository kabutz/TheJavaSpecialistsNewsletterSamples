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

package eu.javaspecialists.tjsn.issue328;
// Java 25:
// 12 seconds: -showversion -verbose:gc -Xmx90g -XX:NewSize=85g
// 85 seconds: -showversion -verbose:gc -Xmx90g
// Java 26:
// 53 seconds: -showversion -verbose:gc -Xmx90g -XX:NewSize=85g
// 193 seconds: -showversion -verbose:gc -Xmx90g

import java.util.concurrent.*;

public class VeryVeryVeryVeryVeryLargeLBD {
    static void main() {
        long time = System.nanoTime();
        try {
            IO.println(Runtime.getRuntime().maxMemory());
            IO.println(new ExtremelyHumongouslyLargeCollection(10));
            var lbd = new LinkedBlockingDeque<Integer>();
            lbd.addAll(new ExtremelyHumongouslyLargeCollection(1L << 31));
            IO.println("lbd.size() = " + lbd.size());
        } finally {
            time = System.nanoTime() - time;
            System.out.printf("time = %dms%n", (time / 1_000_000));
        }
    }
}