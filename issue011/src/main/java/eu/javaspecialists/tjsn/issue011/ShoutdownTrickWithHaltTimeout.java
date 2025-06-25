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

package eu.javaspecialists.tjsn.issue011;

public class ShoutdownTrickWithHaltTimeout {
    public static void main(String args[]) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("You wanna quit, hey?");
                System.out.println("... fry eggs on your CPU.");
                while (true) ;
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {}
                // halt will bail out without calling further shutdown hooks or
                // finalizers
                Runtime.getRuntime().halt(1);
            }
        });
        System.out.println("Let's take a break...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {}
        System.out.println("That's it, I'm outta here");
        System.exit(0);
        System.out.println("This line will not show!");
    }
}
