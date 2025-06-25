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

package eu.javaspecialists.tjsn.issue323;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

// Compile and run with --add-exports java.base/jdk.internal.vm=ALL-UNNAMED
public class PrintingThreadsWithVisitor {
    public static void main(String... args) throws Exception {
        new PrintingThreadsWithVisitor().test();
    }

    private void test() throws Exception {
        Thread.ofPlatform()
                .name("Sleeping Platform Thread")
                .start(this::shortSleep);
        Thread.ofVirtual()
                .name("Sleeping Virtual Thread")
                .start(this::shortSleep);
        Threadable root;
        try (var fixedPool = Executors.newFixedThreadPool(2);
             var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            fixedPool.submit(this::shortSleep);
            scope.fork(this::shortSleep);
            scope.fork(() -> {
                try (var inner = new StructuredTaskScope.ShutdownOnFailure()) {
                    inner.fork(this::shortSleep);
                    inner.join().throwIfFailed();
                    return null;
                }
            });
            root = Threadables.create();
            System.out.println("Pretty print threads:");
            root.accept(new PrintingThreadableVisitor());

            shortSleep();
            shortSleep(); // two should be enough for sleeps to be done

            scope.join().throwIfFailed();
        }
        System.out.println("Pretty print threads again:");
        root.accept(new PrintingThreadableVisitor());
    }

    private Void shortSleep() {
        LockSupport.parkNanos(500_000_000L);
        return null; // return something so we can use it as Callable in fork()
    }
}
