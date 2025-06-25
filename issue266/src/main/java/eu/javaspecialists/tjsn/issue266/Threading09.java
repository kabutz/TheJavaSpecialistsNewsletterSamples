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

package eu.javaspecialists.tjsn.issue266;

public class Threading09 {
    static class Foo {
        public int x, y;
        public boolean f;

        public void bar() {
            x = 5;
            y = 10;
            f = true;
        }
    }

    public static void main(String... args) throws InterruptedException {
        Foo f = new Foo();

        Thread t1 = new Thread(f::bar);
        Thread t2 = new Thread(() -> {
            while (!f.f) {}
            assert (f.x == 5);
        });

        t2.start();
        Thread.sleep(10_000L);

        t1.start();

        t1.join();
        t2.join();
    }
}
