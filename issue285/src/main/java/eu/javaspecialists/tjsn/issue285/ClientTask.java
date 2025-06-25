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

package eu.javaspecialists.tjsn.issue285;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ClientTask implements Runnable {
    private final Socket socket;
    private final boolean verbose;

    public ClientTask(Socket socket, boolean verbose) {
        this.socket = socket;
        this.verbose = verbose;
    }

    private static final byte[] message = "John 3:16\n".getBytes();

    private static final Appendable INITIAL = new Appendable() {
        public Appendable append(CharSequence csq) {
            return new StringBuilder().append(csq);
        }

        public Appendable append(CharSequence csq, int start, int end) {
            return new StringBuilder().append(csq, start, end);
        }

        public Appendable append(char c) {
            return new StringBuilder().append(c);
        }
    };

    public void run() {
        Appendable appendable = INITIAL;
        try (socket;
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()
        ) {
            while (true) {
                for (byte b : message) {
                    out.write(b);
                }
                out.flush();
                TimeUnit.SECONDS.sleep(2);

                for (int i = 0; i < message.length; i++) {
                    int b = in.read();
                    if (verbose) {
                        appendable = appendable.append((char) b);
                    }
                }
                if (verbose) {
                    System.out.print(appendable);
                    appendable = INITIAL;
                }
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception consumeAndExit) {}
    }
}
