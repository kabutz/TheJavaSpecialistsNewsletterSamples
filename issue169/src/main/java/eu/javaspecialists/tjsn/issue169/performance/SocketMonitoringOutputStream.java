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

public class SocketMonitoringOutputStream extends OutputStream {
    private final OutputStream out;
    private final Socket socket;

    public SocketMonitoringOutputStream(Socket socket,
                                        OutputStream out)
            throws IOException {
        this.out = out;
        this.socket = socket;
    }

    public void write(int b) throws IOException {
        out.write(b);
        SocketMonitoringSystem.getInstance().write(socket, b);
    }

    public void write(byte[] b, int off, int length)
            throws IOException {
        out.write(b, off, length);
        SocketMonitoringSystem.getInstance().
                write(socket, b, off, length);
    }
}
