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

class TransmogrifyTask implements Runnable {
    private final Socket socket;

    public TransmogrifyTask(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void run() {
        try (socket;
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream()
        ) {
            while (true) {
                int val = in.read();
                if (Character.isLetter(val))
                    val ^= ' '; // change case of all letters
                out.write(val);
            }
        } catch (IOException e) {
            // connection closed
        }
    }
}
