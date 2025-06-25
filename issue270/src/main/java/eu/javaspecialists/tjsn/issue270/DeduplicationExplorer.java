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

package eu.javaspecialists.tjsn.issue270;

import java.io.*;
import java.lang.management.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

// Java8:
//   -XX:+UseG1GC
//   -XX:+UseStringDeduplication
//   -XX:+PrintStringDeduplicationStatistics
//   -verbose:gc
// Java11:
//   -XX:+UseStringDeduplication
//   -Xlog:stringdedup*=debug
//   -verbose:gc
public class DeduplicationExplorer {
    public static void main(String... args) throws IOException {
        List<String> lines = new ArrayList<>();
        Socket socket = new ServerSocket(8080).accept();
        PrintStream out = new PrintStream(
                socket.getOutputStream(), true);
        out.println("Commands: clear, print, ygc, fgc, close");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
            switch (line) {
                case "clear":
                    lines.clear();
                    break;
                case "print":
                    print(lines);
                    break;
                case "ygc":
                    youngGC();
                    break; // young GC
                case "fgc":
                    System.gc();
                    break; // full GC
                case "close":
                    return;
                default:
                    lines.add(line);
            }
        }
    }

    private static void youngGC() {
        long collectionCount = YOUNG_GC.getCollectionCount();
        do {
            // array is too big to be eliminated with escape analysis
            byte[] bytes = new byte[1024];
        } while (YOUNG_GC.getCollectionCount() == collectionCount);
    }

    private static void print(List<String> lines) {
        System.out.println("lines:");
        lines.forEach(DeduplicationExplorer::print);
    }

    private static void print(String line) {
        try {
            System.out.printf("\t\"%s\" - %s%n", line, VALUE.get(line));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private static final Field VALUE;

    static {
        try {
            VALUE = String.class.getDeclaredField("value");
            VALUE.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    private static final GarbageCollectorMXBean YOUNG_GC;

    static {
        YOUNG_GC = ManagementFactory.getGarbageCollectorMXBeans()
                .stream()
                .filter(pool -> pool.getName().equals(
                        "G1 Young Generation"))
                .findFirst()
                .orElseThrow(() -> new Error("Could not find G1 Young " +
                        "Generation Garbage Collector MXBean"));
    }
}
