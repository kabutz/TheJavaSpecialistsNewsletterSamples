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

import java.io.*;
import java.net.*;

public class ClientTest {
    public static void main(String args[]) throws Exception {
        // Comment out this line if you want to use AspectJ
        SocketMonitoringSystem.initForDelegator();

        BasicSocketMonitor monitor = new BasicSocketMonitor();
        SocketMonitoringSystem.getInstance().add(monitor);

        Socket socket = new Socket("localhost", 8080);
        OutputStream out1 = socket.getOutputStream();
        PrintWriter out = new PrintWriter(out1, true);
        InputStream in1 = socket.getInputStream();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(in1)
        );
        for (int i = 0; i < 13; i++) {
            out.println("Test");
            System.out.println(in.readLine());
            System.out.println(in.readLine());
        }

        monitor.dump();
    }
}
