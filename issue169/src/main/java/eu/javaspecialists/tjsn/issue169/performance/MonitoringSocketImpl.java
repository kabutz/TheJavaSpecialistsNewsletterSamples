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

import eu.javaspecialists.tjsn.issue168.util.*;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;

public class MonitoringSocketImpl extends SocketImpl {
    private final Delegator delegator;

    public MonitoringSocketImpl() throws IOException {
        this.delegator = new Delegator(this, SocketImpl.class,
                "java.net.SocksSocketImpl");
    }

    private Socket getSocket() throws IOException {
        try {
            Field socket = SocketImpl.class.getDeclaredField("socket");
            socket.setAccessible(true);
            return (Socket) socket.get(this);
        } catch (Exception e) {
            throw new IOException("Could not discover real socket");
        }
    }

    public InputStream getInputStream() throws IOException {
        InputStream real = delegator.invoke();
        return new SocketMonitoringInputStream(getSocket(), real);
    }

    public OutputStream getOutputStream() throws IOException {
        OutputStream real = delegator.invoke();
        return new SocketMonitoringOutputStream(getSocket(), real);
    }

    // the rest of the class is plain delegation to real SocketImpl
    public void create(boolean stream) throws IOException {
        delegator.invoke(stream);
    }

    public void connect(String host, int port)
            throws IOException {
        delegator.invoke(host, port);
    }

    // We specify the exact method to delegate to.  Not actually
    // necessary here, but just to show how you would do it.
    public void connect(InetAddress address, int port)
            throws IOException {
        delegator
                .delegateTo("connect", InetAddress.class, int.class)
                .invoke(address, port);
    }

    public void connect(SocketAddress address, int timeout)
            throws IOException {
        delegator.invoke(address, timeout);
    }

    public void bind(InetAddress host, int port)
            throws IOException {
        delegator.invoke(host, port);
    }

    public void listen(int backlog) throws IOException {
        delegator.invoke(backlog);
    }

    public void accept(SocketImpl s) throws IOException {
        delegator.invoke(s);
    }

    public int available() throws IOException {
        Integer result = delegator.invoke();
        return result;
    }

    public void close() throws IOException {
        delegator.invoke();
    }

    public void shutdownInput() throws IOException {
        delegator.invoke();
    }

    public void shutdownOutput() throws IOException {
        delegator.invoke();
    }

    public FileDescriptor getFileDescriptor() {
        return delegator.invoke();
    }

    public InetAddress getInetAddress() {
        return delegator.invoke();
    }

    public int getPort() {
        Integer result = delegator.invoke();
        return result;
    }

    public boolean supportsUrgentData() {
        Boolean result = delegator.invoke();
        return result;
    }

    public void sendUrgentData(int data) throws IOException {
        delegator.invoke(data);
    }

    public int getLocalPort() {
        Integer result = delegator.invoke();
        return result;
    }

    public String toString() {
        return delegator.invoke();
    }

    public void setPerformancePreferences(int connectionTime,
                                          int latency,
                                          int bandwidth) {
        delegator.invoke(connectionTime, latency, bandwidth);
    }

    public void setOption(int optID, Object value)
            throws SocketException {
        delegator.invoke(optID, value);
    }

    public Object getOption(int optID) throws SocketException {
        return delegator.invoke(optID);
    }
}
