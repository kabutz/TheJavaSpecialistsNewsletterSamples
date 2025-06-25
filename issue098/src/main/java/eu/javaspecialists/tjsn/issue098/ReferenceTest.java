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

package eu.javaspecialists.tjsn.issue098;

import java.lang.ref.*;
import java.util.*;

public class ReferenceTest {
    private static final int NUMBER_OF_REFERENCES = 30;

    public static void main(String[] args) {
        // the map contains reference objects as keys, and the id
        // as a value.
        final Map refs = new HashMap();
        final ReferenceQueue queue = new ReferenceQueue();
        // We need a thread that reads processed references from the
        // reference queue so that we can see in what order they are
        // reclaimed.
        new Thread() {
            {
                setDaemon(true);
                start();
            }

            public void run() {
                try {
                    while (true) {
                        Reference ref = queue.remove();
                        Integer id = (Integer) refs.remove(ref);
                        if (ref instanceof SoftReference) {
                            System.out.println("SOFT " + id);
                        } else if (ref instanceof WeakReference) {
                            System.out.println("WEAK " + id);
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        for (int i = 0; i < NUMBER_OF_REFERENCES; i++) {
            System.out.println("NEW  " + i);
            Integer num = new Integer(i);
            // must keep a strong reference to the actual reference,
            // otherwise it will not be enqueued.
            refs.put(new SoftReference(new LargeObject(i), queue), num);
            refs.put(new WeakReference(new LargeObject(i), queue), num);
        }
        byte[][] buf = new byte[1024][];
        System.out.println("Allocating until OOME...");
        for (int i = 0; i < buf.length; i++) {
            buf[i] = new byte[1024 * 1024];
        }
    }
}
