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

package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.net.*;

public class DebuggingOutputStream extends FilterOutputStream {
    // Static data and methods
    private static long totalCount = 0;
    private static long dumpNumber =
            System.currentTimeMillis() / 1000 * 1000;

    private static synchronized String makeFileName() {
        return "dump.written." + dumpNumber++ + ".log";
    }

    public static synchronized long getTotalCount() {
        return totalCount;
    }

    // Non-static data and methods
    private final OutputStream copyStream;
    private long count = 0;

    public DebuggingOutputStream(Socket socket, OutputStream o)
            throws IOException {
        super(o);
        String fileName = makeFileName();
        System.out.println(socket + " -> " + fileName);
        copyStream = new FileOutputStream(fileName);
    }

    public long getCount() {
        return count;
    }

    public void write(int b) throws IOException {
        synchronized (DebuggingOutputStream.class) {
            totalCount++;
        }
        count++;
        out.write(b);
        copyStream.write(b);
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len)
            throws IOException {
        synchronized (DebuggingOutputStream.class) {
            totalCount += len;
        }
        count += len;
        out.write(b, off, len);
        copyStream.write(b, off, len);
    }

    public void close() throws IOException {
        super.close();
        copyStream.close();
    }

    public void flush() throws IOException {
        super.flush();
        copyStream.flush();
    }
}
