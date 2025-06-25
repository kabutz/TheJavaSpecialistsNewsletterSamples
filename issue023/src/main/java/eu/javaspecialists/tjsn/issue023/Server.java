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

public class Server {
    public static final int PORT = 4444;

    public Server(int port) throws IOException {
        ServerSocket ss = new ServerSocket(port);
        while (true) {
            new ServerThread(ss.accept());
        }
    }

    private class ServerThread extends Thread {
        private final Socket socket;

        public ServerThread(Socket socket) {
            this.socket = socket;
            start();
        }

        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(
                        socket.getInputStream());
                while (true) {
                    in.readObject();
                    out.writeObject(new String("test"));
                    out.flush();
                    out.reset();
                }
            } catch (Throwable t) {
                System.out.println("Caught " + t + " - closing thread");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server(PORT);
    }
}