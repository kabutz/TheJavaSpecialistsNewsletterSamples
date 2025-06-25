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

package eu.javaspecialists.tjsn.issue014;

import java.lang.reflect.*;

public class Warper {
    private static Field stringValue;

    static {
        // String has a private array called "value"
        // if it does not, find the array and assign it to value
        try {
            stringValue = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException ex) {
            // safety net in case we are running on a VM with a
            // different name for the array.
            Field[] all = String.class.getDeclaredFields();
            for (int i = 0; stringValue == null && i < all.length; i++) {
                if (all[i].getType().equals(char[].class)
                        || all[i].getType().equals(byte[].class)) {
                    stringValue = all[i];
                }
            }
        }
        if (stringValue != null) {
            stringValue.setAccessible(true); // make field public
        }
        System.out.println("stringValue = " + stringValue);
    }

    public Warper() {
        try {
            stringValue.set(
                    "Romeo, Romeo, wherefore art thou oh Romero?",
                    stringValue.get("Stop this romance nonsense, or I'll be sick"));
            stringValue.set("hi there", stringValue.get("cheers !"));
        } catch (IllegalAccessException ex) {} // shhh
    }
}