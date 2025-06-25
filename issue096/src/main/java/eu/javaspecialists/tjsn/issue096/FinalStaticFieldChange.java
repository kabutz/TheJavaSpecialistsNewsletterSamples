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

package eu.javaspecialists.tjsn.issue096;

import java.lang.reflect.*;

public class FinalStaticFieldChange {
    /**
     * Static fields of type String or primitive would get inlined
     */
    private static final String stringValue = "original value";
    private static final Object objValue = stringValue;

    private static void changeStaticField(String name)
            throws NoSuchFieldException, IllegalAccessException {
        Field statFinField = FinalStaticFieldChange.class.getDeclaredField(name);
        statFinField.setAccessible(true);
        statFinField.set(null, "new Value");
    }

    public static void main(String... args) throws Exception {
        changeStaticField("stringValue");
        changeStaticField("objValue");
        System.out.println("stringValue = " + stringValue);
        System.out.println("objValue = " + objValue);
        System.out.println();
    }
}
