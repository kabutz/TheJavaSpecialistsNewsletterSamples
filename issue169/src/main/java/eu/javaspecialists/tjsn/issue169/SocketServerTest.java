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

package eu.javaspecialists.tjsn.issue169;

import eu.javaspecialists.tjsn.issue169.performance.*;
import eu.javaspecialists.tjsn.issue169.stats.*;

import java.io.*;
import java.net.*;

public class SocketServerTest {
    public static void main(String[] args) throws IOException {
        // Comment out these lines if you want to use AspectJ
        SocketMonitoringSystem.initForDelegator();
        SocketMonitoringSystem.getInstance().add(StatsManager.getSocketStats());

        ServerSocket ss = new ServerSocket(8080);

        while (true) {
            Socket socket = ss.accept();
            System.out.println("socket = " + socket);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            int data;
            while ((data = in.read()) != -1) {
                // write the data twice
                out.write(data);
                out.write(data);
                out.flush();
            }
        }
    }
}
