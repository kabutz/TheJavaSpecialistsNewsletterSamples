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

package eu.javaspecialists.tjsn.issue023;

import java.io.*;
import java.net.*;

public class MultiClient {
    public MultiClient(int port) throws Exception {
        long time = -System.currentTimeMillis();
        Socket[] sockets = new Socket[3500];
        ObjectOutputStream[] outs =
                new ObjectOutputStream[sockets.length];
        ObjectInputStream[] ins =
                new ObjectInputStream[sockets.length];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", port);
            outs[i] = new ObjectOutputStream(
                    sockets[i].getOutputStream());
            ins[i] = new ObjectInputStream(
                    sockets[i].getInputStream());
        }
        System.out.println("Constructed all sockets");
        for (int j = 0; j < 32; j++) {
            long iterationTime = -System.currentTimeMillis();
            for (int i = 0; i < sockets.length; i++) {
                outs[i].writeObject(new Integer(i));
                outs[i].flush();
                outs[i].reset();
            }
            System.out.println(j + ": Written to all sockets");
            for (int i = 0; i < sockets.length; i++) {
                ins[i].readObject();
            }
            System.out.println(j + ": Read from all sockets");
            iterationTime += System.currentTimeMillis();
            System.out.println(j + ": Iteration took " +
                    iterationTime + "ms");
        }
        time += System.currentTimeMillis();
        System.out.println("Writing to " + sockets.length +
                " sockets 32 times took " + time + "ms");
    }

    public static void main(String[] args) throws Exception {
        new MultiClient(Server.PORT);
    }
}