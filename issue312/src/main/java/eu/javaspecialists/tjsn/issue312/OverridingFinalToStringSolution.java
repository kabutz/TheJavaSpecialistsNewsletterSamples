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

package eu.javaspecialists.tjsn.issue312;

public class OverridingFinalToStringSolution {
    private static class Bull {
        public final String toString() {
            return "Bull";
        }
    }

    public static String convert(Bull bull) {
        return "%s".formatted(bull);
    }

    public static void main(String... args) {
        // Don't change anything above this line
        class Bear extends Bull implements java.util.Formattable {
            public void formatTo(java.util.Formatter formatter,
                                 int flags, int width, int precision) {
                formatter.format("Bear");
            }
        }
        Bull bull = new Bear();
        // Don't change anything below this line
        String result = convert(bull);
        if (!result.equals("Bear"))
            throw new AssertionError("Should be \"Bear\"");
        System.out.println("All good!");
    }
}
