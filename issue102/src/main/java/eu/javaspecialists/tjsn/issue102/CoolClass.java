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

package eu.javaspecialists.tjsn.issue102;

import java.lang.reflect.*;

public class CoolClass {
    static {
        try {
            Class[] classes = Integer.class.getDeclaredClasses();
            for (Class clazz : classes) {
                if (clazz.getName().endsWith("IntegerCache")) {
                    Field cacheField = clazz.getDeclaredField("cache");
                    cacheField.setAccessible(true);
                    Integer[] cache = (Integer[]) cacheField.get(null);
                    for (int i = 0; i < cache.length; i++) {
                        cache[i] = new Integer(0);
                    }
                }
            }
        } catch (Throwable e) {
            // we silently pretend we didn't want to destroy Java...
        }
    }

    public static void makeExcellentCuppaCoffee() {
        // let's pretend this function does something amazing,
        // like make a good cup of coffee
        System.out.println("Hmmm, the first cuppa in the morning is the best!");
    }
}
