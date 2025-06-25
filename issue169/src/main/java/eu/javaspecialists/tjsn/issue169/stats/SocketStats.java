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

package eu.javaspecialists.tjsn.issue169.stats;

import eu.javaspecialists.tjsn.issue169.performance.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class SocketStats implements
        SocketStatsMBean, SocketMonitor {
    private final AtomicLong bytesRead = new AtomicLong(0);
    private final AtomicLong bytesWritten = new AtomicLong(0);
    private final ConcurrentMap<Socket, AtomicLong>
            individualBytesRead =
            new ConcurrentHashMap<Socket, AtomicLong>();
    private final ConcurrentMap<Socket, AtomicLong>
            individualBytesWritten =
            new ConcurrentHashMap<Socket, AtomicLong>();

    SocketStats() {
    }

    public void reset() {
        bytesRead.set(0);
        bytesWritten.set(0);
        individualBytesRead.clear();
        individualBytesWritten.clear();
    }

    public long getBytesRead() {
        return bytesRead.longValue();
    }

    public long getBytesWritten() {
        return bytesWritten.longValue();
    }

    public void write(Socket socket, int data) {
        bytesWritten.incrementAndGet();
        increment(socket, 1, individualBytesWritten);
    }

    public void write(Socket socket, byte[] data, int off, int len)
            throws IOException {
        bytesWritten.addAndGet(len);
        increment(socket, len, individualBytesWritten);
    }

    private void increment(Socket socket, int bytes,
                           ConcurrentMap<Socket, AtomicLong> map) {
        AtomicLong counter = map.get(socket);
        if (counter == null) {
            counter = new AtomicLong(0);
            AtomicLong temp = map.putIfAbsent(
                    socket, counter);
            if (temp != null) {
                counter = temp;
            }
        }
        counter.addAndGet(bytes);
    }

    public void read(Socket s, int data) {
        bytesRead.incrementAndGet();
        increment(s, 1, individualBytesRead);
    }

    public void read(Socket s, byte[] data, int off, int len) {
        bytesRead.addAndGet(len);
        increment(s, len, individualBytesRead);
    }

    public String getIndividualBytesWritten() {
        return convertToString(individualBytesWritten);
    }

    public String getIndividualBytesRead() {
        return convertToString(individualBytesRead);
    }

    private String convertToString(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append("\n");
            sb.append("\t\t");
            sb.append(entry.getKey());
            sb.append(" -> ");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    public String toString() {
        return "SocketStats:\n" +
                "\tbytes read    = " + getBytesRead() + "\n" +
                "\tbytes written = " + getBytesWritten() + "\n" +
                "\tindividual bytes read    = " +
                getIndividualBytesRead() + "\n" +
                "\tindividual bytes written = " +
                getIndividualBytesWritten();
    }

    public void printStats() {
        System.out.println(this);
    }
}
