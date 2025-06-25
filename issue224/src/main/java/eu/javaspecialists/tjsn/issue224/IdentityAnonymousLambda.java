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

package eu.javaspecialists.tjsn.issue224;

public class IdentityAnonymousLambda {
    public static void main(String... args) {
        for (int i = 0; i < 2; i++) {
            showIdentity(() ->
                    System.out.println("Lambda - no fields"));
            showIdentity(() ->
                    System.out.println("Lambda - parameters - " + args));
            showIdentity(new Runnable() {
                public void run() {
                    System.out.println("anon - no fields");
                }
            });
            showIdentity(new Runnable() {
                public void run() {
                    System.out.println("anon - parameters - " + args);
                }
            });
            System.out.println();
        }
    }

    private static void showIdentity(Runnable runnable) {
        System.out.printf("%x ", System.identityHashCode(runnable));
        runnable.run();
    }
}
