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

public class Sucker {
    private final String outputFile;
    private final Stats stats;
    private final URL url;

    public Sucker(String path, String outputFile) throws IOException {
        this.outputFile = outputFile;
        System.out.println(new Date() + " Constructing Sucker");
        url = new URL(path);
        System.out.println(new Date() + " Connected to URL");
        stats = Stats.make(url);
    }

    public Sucker(String path) throws IOException {
        this(path, path.replaceAll(".*\\/", ""));
    }

    private void downloadFile() throws IOException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                stats.print();
            }
        }, 1000, 1000);

        try {
            System.out.println(new Date() + " Opening Streams");
            InputStream in = url.openStream();
            OutputStream out = new FileOutputStream(outputFile);
            System.out.println(new Date() + " Streams opened");

            byte[] buf = new byte[1024 * 1024];
            int length;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
                stats.bytes(length);
            }
            in.close();
            out.close();
        } finally {
            timer.cancel();
            stats.print();
        }
    }

    private static void usage() {
        System.out.println("Usage: java Sucker URL [targetfile]");
        System.out.println("\tThis will download the file at the URL " +
                "to the targetfile location");
        System.exit(1);
    }

    public static void main(String... args) throws IOException {
        Sucker sucker;
        switch (args.length) {
            case 1:
                sucker = new Sucker(args[0]);
                break;
            case 2:
                sucker = new Sucker(args[0], args[1]);
                break;
            default:
                usage();
                return;
        }
        sucker.downloadFile();
    }
}
