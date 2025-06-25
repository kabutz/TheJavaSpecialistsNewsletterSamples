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


import java.io.*;
import java.net.*;

public class SocketMonitoringInputStream extends InputStream {
    private final Socket socket;
    private final InputStream in;

    public SocketMonitoringInputStream(Socket socket,
                                       InputStream in)
            throws IOException {
        this.socket = socket;
        this.in = in;
    }

    public int read() throws IOException {
        int result = in.read();
        if (result != -1) {
            SocketMonitoringSystem.getInstance().read(socket, result);
        }
        return result;
    }

    public int read(byte[] b, int off, int len)
            throws IOException {
        int length = in.read(b, off, len);
        if (length != -1) {
            SocketMonitoringSystem.getInstance().
                    read(socket, b, off, length);
        }
        return length;
    }
}
