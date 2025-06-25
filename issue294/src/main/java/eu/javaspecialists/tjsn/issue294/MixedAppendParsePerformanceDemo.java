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

package eu.javaspecialists.tjsn.issue294;

import java.lang.invoke.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// run with
// -showversion --add-opens java.base/java.util=ALL-UNNAMED
// -Xmx12g -Xms12g -XX:+UseParallelGC -XX:+AlwaysPreTouch
// -verbose:gc
public class MixedAppendParsePerformanceDemo {
    private static final Map<String, LongAccumulator> bestResults =
            new ConcurrentSkipListMap<>();

    public static void main(String... args) {
        String[] formats = {
                // should be faster
                "1. this does not have any percentages at all",
                // should be faster
                "2. this %s has only a simple field",
                // might be slower
                "3. this has a simple field %s and then a complex %-20s",
                // no idea
                "4. %s %1s %2s %3s %4s %5s %10s %22s",
        };

        System.out.println("Warmup:");
        run(formats, 5);
        System.out.println();

        bestResults.clear();

        System.out.println("Run:");
        run(formats, 10);
        System.out.println();

        System.out.println("Best results:");
        bestResults.forEach((format, best) ->
                System.out.printf("%s%n\t%dms%n", format,
                        best.longValue()));
    }

    private static void run(String[] formats, int runs) {
        for (int i = 0; i < runs; i++) {
            for (String format : formats) {
                Formatter formatter = new Formatter();
                test(formatter, format);
            }
            System.gc();
            System.out.println();
        }
    }

    private static void test(Formatter formatter, String format) {
        System.out.println(format);
        long time = System.nanoTime();
        try {
            for (int i = 0; i < 1_000_000; i++) {
                parseMH.invoke(formatter, format);
            }
        } catch (Throwable throwable) {
            throw new AssertionError(throwable);
        } finally {
            time = System.nanoTime() - time;
            bestResults.computeIfAbsent(format, key ->
                            new LongAccumulator(Long::min, Long.MAX_VALUE))
                    .accumulate(time / 1_000_000);
            System.out.printf("\t%dms%n", (time / 1_000_000));
        }
    }

    private static final MethodHandle parseMH;

    static {
        try {
            parseMH = MethodHandles.privateLookupIn(Formatter.class,
                            MethodHandles.lookup())
                    .findVirtual(Formatter.class, "parse",
                            MethodType.methodType(List.class, String.class));
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }
}
