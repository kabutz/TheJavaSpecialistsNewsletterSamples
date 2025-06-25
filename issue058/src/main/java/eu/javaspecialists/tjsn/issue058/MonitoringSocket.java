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

package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.lang.ref.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

public class MonitoringSocket extends Socket {
    // keep a list of active sockets, referenced by SoftReference
    private static final List sockets = new LinkedList();

    private static void dump() {
        System.out.println("Socket dump:");
        System.out.println("------------");
        System.out.println("Total bytes"
                + " read=" + DebuggingInputStream.getTotalCount()
                + ", written=" + DebuggingOutputStream.getTotalCount());
        // print all the sockets, and remove them if the Soft
        // Reference has been set to null.
        synchronized (sockets) {
            Iterator it = sockets.iterator();
            while (it.hasNext()) {
                SoftReference ref = (SoftReference) it.next();
                MonitoringSocket socket = (MonitoringSocket) ref.get();
                if (socket == null)
                    it.remove();
                else if (!socket.isImplNull())
                    System.out.println(socket);
            }
        }
        System.out.println();
    }

    private static Field socket_impl = null;

    static {
        try {
            socket_impl = Socket.class.getDeclaredField("impl");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException();
        }
        socket_impl.setAccessible(true);
    }

    // Sometimes, the Socket.impl data member gets set to null
    // by the ServerSocket.  Yes, it is ugly, but I did not write
    // the java.net.* package ;-)
    private boolean isImplNull() {
        try {
            return null == socket_impl.get(this);
        } catch (Exception ex) {
            return true;
        }
    }

    static {
        new Thread("Socket Monitor") {
            {
                setDaemon(true);
                start();
            }

            public void run() {
                try {
                    while (true) {
                        try {
                            sleep(5000);
                            dump();
                        } catch (RuntimeException ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (InterruptedException e) {} // exit thread
            }
        };
    }

    private DebuggingInputStream din;
    private DebuggingOutputStream dout;

    { // initializer block
        synchronized (sockets) {
            sockets.add(new SoftReference(this));
        }
    }

    public MonitoringSocket() {
    }

    public MonitoringSocket(String host, int port)
            throws UnknownHostException, IOException {
        super(host, port);
    }

    private long getBytesRead() {
        return din == null ? 0 : din.getCount();
    }

    private long getBytesWritten() {
        return dout == null ? 0 : dout.getCount();
    }

    public synchronized void close() throws IOException {
        synchronized (sockets) {
            Iterator it = sockets.iterator();
            while (it.hasNext()) {
                SoftReference ref = (SoftReference) it.next();
                if (ref.get() == null || ref.get() == this) {
                    it.remove();
                }
            }
        }
        super.close();
        if (din != null) {
            din.close();
            din = null;
        }
        if (dout != null) {
            dout.close();
            dout = null;
        }
    }

    public InputStream getInputStream() throws IOException {
        if (din != null) return din;
        return din =
                new DebuggingInputStream(this, super.getInputStream());
    }

    public OutputStream getOutputStream() throws IOException {
        if (dout != null) return dout;
        return dout =
                new DebuggingOutputStream(this, super.getOutputStream());
    }

    public String toString() {
        return super.toString()
                + " read=" + getBytesRead()
                + ", written=" + getBytesWritten();
    }
}
