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

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class MethodDistinctify {
    public static void main(String... args) {
        System.out.println("Normal ArrayDeque clone() Methods:");
        EnhancedStream.of(ArrayDeque.class.getMethods())
                .filter(method -> method.getName().equals("clone"))
                .forEach(MethodDistinctify::print);

        System.out.println();

        System.out.println("Distinct ArrayDeque:");
        EnhancedStream.of(ArrayDeque.class.getMethods())
                .filter(method -> method.getName().equals("clone"))
                .distinct(HASH_CODE, EQUALS, MERGE)
                .forEach(MethodDistinctify::print);

        System.out.println();

        System.out.println("Normal ConcurrentSkipListSet:");
        EnhancedStream.of(ConcurrentSkipListSet.class.getMethods())
                .filter(method -> method.getName().contains("Set"))
                .sorted(METHOD_COMPARATOR)
                .forEach(MethodDistinctify::print);

        System.out.println();

        System.out.println("Distinct ConcurrentSkipListSet:");
        EnhancedStream.of(ConcurrentSkipListSet.class.getMethods())
                .filter(method -> method.getName().contains("Set"))
                .distinct(HASH_CODE, EQUALS, MERGE)
                .sorted(METHOD_COMPARATOR)
                .forEach(MethodDistinctify::print);
    }

    private static void print(Method m) {
        System.out.println(
                Stream.of(m.getParameterTypes())
                        .map(Class::getSimpleName)
                        .collect(Collectors.joining(
                                ", ",
                                "  " + m.getReturnType().getSimpleName()
                                        + " " + m.getName() + "(",
                                ")"))
        );
    }

    public static final ToIntFunction<Method> HASH_CODE =
            method -> method.getName().hashCode() +
                    method.getParameterCount();

    public static final BiPredicate<Method, Method> EQUALS =
            (method1, method2) ->
                    method1.getName().equals(method2.getName()) &&
                            method1.getParameterCount() ==
                                    method2.getParameterCount() &&
                            Arrays.equals(method1.getParameterTypes(),
                                    method2.getParameterTypes());

    public static final BinaryOperator<Method> MERGE =
            (method1, method2) -> {
                if (method1.getReturnType()
                        .isAssignableFrom(method2.getReturnType()))
                    return method2;
                if (method2.getReturnType()
                        .isAssignableFrom(method1.getReturnType()))
                    return method1;
                throw new IllegalArgumentException(
                        "Conflicting return types " +
                                method1.getReturnType().getCanonicalName() +
                                " and " +
                                method2.getReturnType().getCanonicalName());
            };

    public static final Comparator<Method> METHOD_COMPARATOR =
            Comparator.comparing(Method::getName)
                    .thenComparing(method ->
                            Arrays.toString(method.getParameterTypes()));
}
