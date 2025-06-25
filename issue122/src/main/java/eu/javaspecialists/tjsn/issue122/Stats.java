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

package eu.javaspecialists.tjsn.issue122;

import java.io.*;
import java.net.*;
import java.util.*;

public abstract class Stats {
    private volatile int totalBytes;
    private long start = System.currentTimeMillis();

    public int seconds() {
        int result = (int) ((System.currentTimeMillis() - start) / 1000);
        return result == 0 ? 1 : result; // avoid div by zero
    }

    public void bytes(int length) {
        totalBytes += length;
    }

    public void print() {
        int kbpersecond = (int) (totalBytes / seconds() / 1024);
        System.out.printf("%10d KB%5s%%  (%d KB/s)%n", totalBytes / 1024,
                calculatePercentageComplete(totalBytes), kbpersecond);
    }

    public abstract String calculatePercentageComplete(int bytes);

    public static Stats make(URL url) throws IOException {
        System.out.println(new Date() + " Opening connection to URL");
        URLConnection con = url.openConnection();
        System.out.println(new Date() + " Getting content length");
        int size = con.getContentLength();
        return size == -1 ? new BasicStats() : new ProgressStats(size);
    }
}
