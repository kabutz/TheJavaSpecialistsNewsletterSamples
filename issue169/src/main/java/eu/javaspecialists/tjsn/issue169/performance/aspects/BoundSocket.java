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
