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

package eu.javaspecialists.tjsn.issue272;

import java.lang.invoke.*;
import java.lang.reflect.*;

// run with --add-opens java.base/java.lang.reflect=ALL-UNNAMED
public class HiddenFieldsRevealed {
    private static final Object greeting = "Hello world";

    public static void main(String... args)
            throws ReflectiveOperationException {
        VarHandle vh = MethodHandles.privateLookupIn(
                Field.class, MethodHandles.lookup()
        ).findVarHandle(Field.class, "modifiers", int.class);
        Field greetingField = HiddenFieldsRevealed.class
                .getDeclaredField("greeting");
        System.out.println("isFinal=" +
                Modifier.isFinal(greetingField.getModifiers()));
        vh.set(greetingField, 0);
        System.out.println("isFinal=" +
                Modifier.isFinal(greetingField.getModifiers()));
    }
}
