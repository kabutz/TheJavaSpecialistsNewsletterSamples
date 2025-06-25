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

package eu.javaspecialists.tjsn.issue315;

import java.lang.reflect.*;
import java.util.*;
import java.util.random.*;
import java.util.stream.*;

public class ShowGenerators {
    public static void main(String... args) {
        Stream.of(RandomGeneratorFactory.class.getMethods())
                .filter(method -> method.getName().startsWith("is"))
                .sorted(Comparator.comparing(Method::getName))
                .forEach(ShowGenerators::printAttributeDetails);
    }

    private static void printAttributeDetails(Method method) {
        System.out.println(method.getName() + ":");
        RandomGeneratorFactory.all()
                .filter(factory -> {
                    try {
                        return (boolean) method.invoke(factory);
                    } catch (ReflectiveOperationException e) {
                        throw new IllegalStateException(e);
                    }
                })
                .map(RandomGeneratorFactory::create)
                .map(Object::getClass)
                .map(Class::getSimpleName)
                .sorted()
                .map("\t%s"::formatted)
                .forEach(System.out::println);
    }
}
