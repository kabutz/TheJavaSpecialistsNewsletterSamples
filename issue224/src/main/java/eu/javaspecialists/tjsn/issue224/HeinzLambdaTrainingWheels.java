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

import java.util.*;
import java.util.function.*;

public class HeinzLambdaTrainingWheels {
    public static void main(String... args) {
        // step 1. write using old-school anonymous inner classes
        Arrays.stream(args).map(new Function<String, String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        }).forEach(new Consumer<String>() {
            public void accept(String s) {
                System.out.println(s);
            }
        });

        // step 2. "Replace with lambda"
        Arrays.stream(args).map(s -> s.toUpperCase()).
                forEach(s -> System.out.println(s));

        // step 3. "Replace with Method Reference"
        Arrays.stream(args).map(String::toUpperCase).
                forEach(System.out::println);
    }
}
