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

public class SophisticatedShutdownSequence {
    private static boolean running = true;

    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("reached point of no return ...");
            }
        });

        SignalHandler handler = new SignalHandler() {
            public void handle(Signal sig) {
                if (running) {
                    running = false;
                    System.out.println("Signal " + sig);
                    System.out.println("Shutting down database...");
                } else {
                    // only on the second attempt do we exit
                    System.out.println(" database shutdown interrupted!");
                    System.exit(0);
                }
            }
        };
        Signal.handle(new Signal("INT"), handler);
        Signal.handle(new Signal("TERM"), handler);
    }

    public static void main(String args[]) throws Exception {
        init();
        Object o = new Object();
        synchronized (o) {
            o.wait(10000);
        }
        System.exit(0);
    }
}
