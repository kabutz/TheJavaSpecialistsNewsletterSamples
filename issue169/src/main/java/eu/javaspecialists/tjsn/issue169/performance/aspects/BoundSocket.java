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

package eu.javaspecialists.tjsn.issue169.performance.aspects;

import eu.javaspecialists.tjsn.issue169.performance.*;
import eu.javaspecialists.tjsn.issue169.stats.*;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;

import java.io.*;
import java.net.*;

@Aspect
public class BoundSocket {
    public BoundSocket() {
        SocketMonitoringSystem.getInstance().add(
                StatsManager.getSocketStats());
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                StatsManager.getSocketStats().printStats();
            }
        });
    }

    @Pointcut("call(* java.net.Socket.getInputStream()) && target(s)")
    void input(Socket s) {
    }

    @Around("input(s)")
    public Object wrapInputStream(ProceedingJoinPoint joinPoint,
                                  Socket s)
            throws Throwable {
        InputStream in = (InputStream) joinPoint.proceed();
        return new SocketMonitoringInputStream(s, in);
    }

    @Pointcut("call(* java.net.Socket.getOutputStream()) && target(s)")
    void output(Socket s) {
    }

    @Around("output(s)")
    public Object wrapOutputStream(ProceedingJoinPoint joinPoint,
                                   Socket s)
            throws Throwable {
        OutputStream out = (OutputStream) joinPoint.proceed();
        return new SocketMonitoringOutputStream(s, out);
    }
}
