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

package eu.javaspecialists.tjsn.issue203;

import java.util.concurrent.*;

public class FinallyJava {
    public void foo() {
        try {
            if (ThreadLocalRandom.current().nextBoolean()) {
                System.out.println("First random true");
                return;
            }
            if (ThreadLocalRandom.current().nextBoolean()) {
                System.out.println("Second random true");
                return;
            }
            System.out.println("Both randoms false");
        } finally {
            System.out.println("Done");
        }
    }

    public static void main(String... args) {
        new FinallyJava().foo();
    }
}
