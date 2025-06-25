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

package eu.javaspecialists.tjsn.issue102;

// --add-opens java.base/java.lang=ALL-UNNAMED
public class Arithmetic {
    public static void main(String... args) {
        CoolClass.makeExcellentCuppaCoffee(); // hmmm - we all need that!
        int upto = 1000;

        // using Integer
        Integer total1 = 0;
        for (int i = 1; i <= upto; i++) {
            total1 += i;
        }
        System.out.println("total1 = " + total1);

        // using int
        int total2 = 0;
        for (int i = 1; i <= upto; i++) {
            total2 += i;
        }
        System.out.println("total2 = " + total2);

        System.out.println("Should be " + ((upto + 1) * upto) / 2);
    }
}
