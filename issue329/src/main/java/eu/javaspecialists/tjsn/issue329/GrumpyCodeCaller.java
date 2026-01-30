/*
 * Copyright 2000-2026 Heinz Max Kabutz
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

package eu.javaspecialists.tjsn.issue329;

import module java.base;

public class GrumpyCodeCaller {
    public void callMethodsByRandom(
            InvocationTargetSantizier sanitizer)
            throws IllegalAccessException {
        System.out.println("Using sanitizer " +
                           sanitizer.getClass());
        var grumpy = new GrumpyCode();
        var methods = GrumpyCode.class.getDeclaredMethods();
        for (int i = 0; i < 10; i++) {
            var method = methods[
                    ThreadLocalRandom.current()
                            .nextInt(methods.length)];
            try {
                method.invoke(grumpy);
            } catch (InvocationTargetException e) {
                try {
                    sanitizer.sanitize(e);
                } catch (Error | RuntimeException ex) {
                    System.out.println(ex);
                }
            }
        }
        System.out.println();
    }

    void main() throws ReflectiveOperationException {
        for (Class<?> sanitizerClass :
                InvocationTargetSantizier.class
                        .getPermittedSubclasses()) {
            var subclass = sanitizerClass.asSubclass(
                    InvocationTargetSantizier.class);
            var sanitizer = subclass
                    .getConstructor()
                    .newInstance();
            callMethodsByRandom(sanitizer);
        }
    }
}
