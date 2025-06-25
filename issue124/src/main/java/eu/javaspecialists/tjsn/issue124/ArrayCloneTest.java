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

package eu.javaspecialists.tjsn.issue124;

import java.util.*;

public class ArrayCloneTest implements Runnable {
    private final byte[] byteValue;

    public ArrayCloneTest(int length) {
        byteValue = new byte[length];
        // always the same set of bytes...
        new Random(0).nextBytes(byteValue);
    }

    public void run() {
        byte[] result = byteValue.clone();
    }
}