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

package eu.javaspecialists.tjsn.issue128;

public class SuDoKuTest {
    public static void main(String... args) {
        SuDoKu gb = new SuDoKu( // Cape Times Mon 2006/06/19
                2, 0, 0, 9, 0, 6, 0, 0, 4,
                0, 0, 5, 0, 7, 0, 9, 0, 0,
                0, 3, 0, 0, 0, 0, 0, 8, 0,
                0, 0, 3, 4, 0, 7, 8, 0, 0,
                8, 9, 0, 2, 0, 5, 0, 6, 3,
                0, 0, 7, 6, 0, 8, 2, 0, 0,
                0, 7, 0, 0, 0, 0, 0, 2, 0,
                0, 0, 8, 0, 6, 0, 1, 0, 0,
                3, 0, 0, 7, 0, 1, 0, 0, 8);
        if (gb.solve()) {
            System.out.println("SOLVED!!!");
        } else {
            System.out.println("Could not solve");
        }
    }
}
