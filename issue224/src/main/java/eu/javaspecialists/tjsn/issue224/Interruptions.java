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

public class Interruptions {
    public static void saveForLater(InterruptibleAction action) {
        saveForLater(action::run);
    }
    /* Previously
    public static void saveForLater(InterruptibleAction action) {
        saveForLater(new InterruptibleAction() {
            public void run() throws InterruptedException {
                action.run();
            }
        });
    }
     */

    public static <E> E saveForLater(
            InterruptibleTask<E> task) {
        boolean interrupted = Thread.interrupted(); // clears flag
        try {
            while (true) {
                try {
                    return task.run();
                } catch (InterruptedException e) {
                    // flag would be cleared at this point too
                    interrupted = true;
                }
            }
        } finally {
            if (interrupted) Thread.currentThread().interrupt();
        }
    }

    @FunctionalInterface
    public interface InterruptibleAction {
        public void run() throws InterruptedException;
    }

    @FunctionalInterface
    public interface InterruptibleTask<E> {
        public E run() throws InterruptedException;
    }
}
