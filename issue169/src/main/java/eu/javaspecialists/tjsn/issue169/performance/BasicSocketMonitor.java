package eu.javaspecialists.tjsn.issue169.performance;

import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class BasicSocketMonitor implements SocketMonitor {
    private Map<Socket, AtomicLong> bytesWritten =
            new WeakHashMap<Socket, AtomicLong>();
    private Map<Socket, AtomicLong> bytesRead =
            new WeakHashMap<Socket, AtomicLong>();

    public void dump() {
        long totalWritten = 0;
        for (AtomicLong written : bytesWritten.values()) {
            totalWritten += written.longValue();
        }

        long totalRead = 0;
        for (AtomicLong read : bytesRead.values()) {
            totalRead += read.longValue();
        }

        System.out.println("BasicSocketMonitor:");
        System.out.println("\tbytes read      = " + totalRead);
        System.out.println("\tbytes written   = " + totalWritten);
        System.out.println("\tindividual bytes read    =");
        for (Map.Entry<Socket, AtomicLong> entry : bytesRead.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + " -> " +
                    entry.getValue());
        }
        System.out.println("\tindividual bytes written =");
        for (Map.Entry<Socket, AtomicLong> entry : bytesWritten.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + " -> " +
                    entry.getValue());
        }
    }

    public void write(Socket s, int data) {
        getWrites(s).incrementAndGet();
    }

    private AtomicLong getWrites(Socket s) {
        AtomicLong writes;
        synchronized (this) {
            writes = bytesWritten.get(s);
            if (writes == null) {
                writes = new AtomicLong();
                bytesWritten.put(s, writes);
            }
        }
        return writes;
    }

    private AtomicLong getReads(Socket s) {
        AtomicLong reads;
        synchronized (this) {
            reads = bytesRead.get(s);
            if (reads == null) {
                reads = new AtomicLong();
                bytesRead.put(s, reads);
            }
        }
        return reads;
    }

    public void write(Socket s, byte[] data, int offset, int length) {
        getWrites(s).addAndGet(length);
    }

    public void read(Socket s, int data) {
        getReads(s).incrementAndGet();
    }

    public void read(Socket s, byte[] data, int offset, int length) {
        getReads(s).addAndGet(length);
    }
}