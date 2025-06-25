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

package eu.javaspecialists.tjsn.issue300;

import eu.javaspecialists.tjsn.issue300.Colour.*;

public class IfElseInstanceof {
    public static void main(String... args) {
        Colour[] colours = {new Red(), new Red(), new Green(),
                new Blue(), new Green(), new Orange()};
        for (Colour colour : colours) {
            if (colour instanceof Red) {
                System.out.println("#FF0000 // Red");
            } else if (colour instanceof Green) {
                System.out.println("#008000 // Green");
            } else if (colour instanceof Blue) {
                System.out.println("#0000FF // Blue");
                break; // stop when we encounter the first Blue
            } else if (colour instanceof Orange) {
                System.out.println("#FFA500 // Orange");
            } else {
                throw new AssertionError(
                        "Unknown Colour: " + colour.getClass());
            }
        }
    }
}
