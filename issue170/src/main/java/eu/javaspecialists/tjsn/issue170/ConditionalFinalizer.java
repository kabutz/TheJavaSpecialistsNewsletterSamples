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

package eu.javaspecialists.tjsn.issue170;

public class ConditionalFinalizer {
    private static final boolean DEBUG = true;

    // Should be volatile as it is accessed from multiple threads.
    // Thanks to Anton Muhin for pointing that out.
    private volatile boolean resourceClosed;
    private final int id;

    public ConditionalFinalizer(int id) {
        this.id = id;
        resourceClosed = false;
    }

    protected void finalize() throws Throwable {
        if (DEBUG) {
            if (!resourceClosed) {
                System.err.println(
                        "You forgot to close the resource with id " + id);
                close();
            }
            super.finalize();
        }
    }

    public void close() {
        resourceClosed = true;
    }
}
