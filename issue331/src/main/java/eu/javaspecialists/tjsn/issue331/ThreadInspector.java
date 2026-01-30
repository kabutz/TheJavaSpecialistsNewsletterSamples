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

package eu.javaspecialists.tjsn.issue331;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

// --add-opens java.base/java.lang=ALL-UNNAMED
public class ThreadInspector {
    private static final Map<Integer, String> virtualThreadStates;
    private static final VarHandle STATE;

    private static final Predicate<Field> INT_TYPE =
            field -> field.getType() == int.class;

    private static final Set<AccessFlag> ACCESS_FLAGS =
            Set.of(AccessFlag.PRIVATE, AccessFlag.STATIC,
                    AccessFlag.FINAL);

    private static final Predicate<Field> PRIVATE_STATIC_FINAL =
            field -> field.accessFlags().equals(
                    ACCESS_FLAGS);

    // To support older versions of Java, such as Java 21 to 23
    private static final Predicate<Field> NOT_TRACE_PINNING_MODE =
            field -> !field.getName().equals("TRACE_PINNING_MODE");

    static {
        var vthreadClass = Thread.ofVirtual()
                .unstarted(() -> {
                })
                .getClass();
        virtualThreadStates = Stream.of(
                        vthreadClass.getDeclaredFields())
                .filter(INT_TYPE)
                .filter(PRIVATE_STATIC_FINAL)
                .filter(NOT_TRACE_PINNING_MODE)
                .collect(Collectors.toMap(
                        ThreadInspector::getStateValue,
                        Field::getName
                ));
        try {
            STATE = MethodHandles.privateLookupIn(vthreadClass,
                            MethodHandles.lookup())
                    .findVarHandle(vthreadClass,
                            "state", int.class);
        } catch (ReflectiveOperationException e) {
            throw new Error(e);
        }
    }

    private static int getStateValue(Field field) {
        try {
            field.setAccessible(true);
            return (int) field.get(null);
        } catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static String getCompoundThreadStates(Thread thread) {
        return (thread.isVirtual() ? virtualThreadStates.get(
                STATE.get(thread)) + "/" : "") + thread.getState();
    }
}
