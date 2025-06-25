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

class ClientTaskWithIOStreams implements Runnable {
    private final Socket socket;
    private final boolean verbose;

    public ClientTaskWithIOStreams(Socket socket, boolean verbose) {
        this.socket = socket;
        this.verbose = verbose;
    }

    private static final String message = "John 3:16";

    public void run() {
        try (socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()));
             PrintStream out = new PrintStream(
                     socket.getOutputStream(), true)
        ) {
            while (true) {
                out.println(message);
                TimeUnit.SECONDS.sleep(2);
                String reply = in.readLine();
                if (verbose) System.out.println(reply);
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception consumeAndExit) {}
    }
}
