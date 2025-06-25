package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.net.*;

public class DebuggingOutputStream extends FilterOutputStream {
    // Static data and methods
    private static long totalCount = 0;
    private static long dumpNumber =
            System.currentTimeMillis() / 1000 * 1000;

    private static synchronized String makeFileName() {
        return "dump.written." + dumpNumber++ + ".log";
    }

    public static synchronized long getTotalCount() {
        return totalCount;
    }

    // Non-static data and methods
    private final OutputStream copyStream;
    private long count = 0;

    public DebuggingOutputStream(Socket socket, OutputStream o)
            throws IOException {
        super(o);
        String fileName = makeFileName();
        System.out.println(socket + " -> " + fileName);
        copyStream = new FileOutputStream(fileName);
    }

    public long getCount() {
        return count;
    }

    public void write(int b) throws IOException {
        synchronized (DebuggingOutputStream.class) {
            totalCount++;
        }
        count++;
        out.write(b);
        copyStream.write(b);
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len)
            throws IOException {
        synchronized (DebuggingOutputStream.class) {
            totalCount += len;
        }
        count += len;
        out.write(b, off, len);
        copyStream.write(b, off, len);
    }

    public void close() throws IOException {
        super.close();
        copyStream.close();
    }

    public void flush() throws IOException {
        super.flush();
        copyStream.flush();
    }
}
