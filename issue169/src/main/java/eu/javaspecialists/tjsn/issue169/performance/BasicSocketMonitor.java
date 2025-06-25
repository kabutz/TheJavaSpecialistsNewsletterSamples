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

package eu.javaspecialists.tjsn.issue169.performance;

import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class BasicSocketMonitor implements SocketMonitor {
    private Map<Socket, AtomicLong> bytesWritten =
            new WeakHashMap<Socket, AtomicLong>();
    private Map<Socket, AtomicLong> bytesRead =
            new WeakHashMap<Socket, AtomicLong>();

    public void dump() {
        long totalWritten = 0;
        for (AtomicLong written : bytesWritten.values()) {
            totalWritten += written.longValue();
        }

        long totalRead = 0;
        for (AtomicLong read : bytesRead.values()) {
            totalRead += read.longValue();
        }

        System.out.println("BasicSocketMonitor:");
        System.out.println("\tbytes read      = " + totalRead);
        System.out.println("\tbytes written   = " + totalWritten);
        System.out.println("\tindividual bytes read    =");
        for (Map.Entry<Socket, AtomicLong> entry : bytesRead.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + " -> " +
                    entry.getValue());
        }
        System.out.println("\tindividual bytes written =");
        for (Map.Entry<Socket, AtomicLong> entry : bytesWritten.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + " -> " +
                    entry.getValue());
        }
    }

    public void write(Socket s, int data) {
        getWrites(s).incrementAndGet();
    }

    private AtomicLong getWrites(Socket s) {
        AtomicLong writes;
        synchronized (this) {
            writes = bytesWritten.get(s);
            if (writes == null) {
                writes = new AtomicLong();
                bytesWritten.put(s, writes);
            }
        }
        return writes;
    }

    private AtomicLong getReads(Socket s) {
        AtomicLong reads;
        synchronized (this) {
            reads = bytesRead.get(s);
            if (reads == null) {
                reads = new AtomicLong();
                bytesRead.put(s, reads);
            }
        }
        return reads;
    }

    public void write(Socket s, byte[] data, int offset, int length) {
        getWrites(s).addAndGet(length);
    }

    public void read(Socket s, int data) {
        getReads(s).incrementAndGet();
    }

    public void read(Socket s, byte[] data, int offset, int length) {
        getReads(s).addAndGet(length);
    }
}