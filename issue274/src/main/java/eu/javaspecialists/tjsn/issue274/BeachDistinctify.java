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

package eu.javaspecialists.tjsn.issue274;

import java.util.function.*;

public class BeachDistinctify {
    public static void main(String... args) {
        EnhancedStream.of("Kalathas", "Stavros", "STAVROS",
                        "marathi", "kalathas", "baLos", "Balos")
                .distinct(HASH_CODE, EQUALS, MERGE)
                .forEach(System.out::println);
    }

    // case insensitive hashCode() and equals()
    public static final
    ToIntFunction<String> HASH_CODE =
            s -> s.toUpperCase().hashCode();
    public static final
    BiPredicate<String, String> EQUALS =
            (s1, s2) ->
                    s1.toUpperCase().equals(s2.toUpperCase());

    // keep the string with the highest total ascii value
    public static final
    BinaryOperator<String> MERGE =
            (s1, s2) ->
                    s1.chars().sum() < s2.chars().sum() ? s2 : s1;
}
