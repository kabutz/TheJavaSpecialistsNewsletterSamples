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

package eu.javaspecialists.tjsn.issue043;

import sun.misc.*;

/**
 * The idea for this code came from James Pereira, this code
 * looks quite different to the well-structured, logically
 * named class he sent me.
 * The idea is that we register a handler for a Ctrl+C
 * signal and then handle it.
 */
public class Aaarggh {
    public static void main(String[] args) throws Exception {
        Signal.handle(new Signal("INT"), new SignalHandler() {
            public void handle(Signal sig) {
                System.out.println(
                        "Aaarggh, a user is trying to interrupt me!!");
                System.out.println(
                        "(throw garlic at user, say `shoo, go away')");
            }
        });
        for (int i = 0; i < 100; i++) {
            Thread.sleep(1000);
            System.out.print('.');
        }
    }
}
