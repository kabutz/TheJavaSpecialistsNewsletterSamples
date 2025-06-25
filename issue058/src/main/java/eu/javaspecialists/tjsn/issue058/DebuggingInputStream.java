package eu.javaspecialists.tjsn.issue058;

import java.io.*;
import java.net.*;

/**
 * This class counts the number of bytes read by it before
 * passing them on to the next Inputstream in the IO chain.
 * It also dumps the bytes read into a file.
 * Should probably specify a factory for making the file
 * names, however, there is enough stuff to show here without
 * such an extra.
 */
public class DebuggingInputStream extends FilterInputStream {
    // Static data and methods
    private static long totalCount = 0;
    private static long dumpNumber =
            System.currentTimeMillis() / 1000 * 1000;

    private static synchronized String makeFileName() {
        return "dump.read." + dumpNumber++ + ".log";
    }

    public static synchronized long getTotalCount() {
        return totalCount;
    }

    // Non-static data and methods
    private final OutputStream copyStream;
    private long count = 0;

    public DebuggingInputStream(Socket socket, InputStream in)
            throws IOException {
        super(in);
        String fileName = makeFileName();
        System.out.println(socket + " -> " + fileName);
        copyStream = new FileOutputStream(fileName);
    }

    public long getCount() {
        return count;
    }

    public int read() throws IOException {
        int result = in.read();
        if (result != -1) {
            synchronized (DebuggingInputStream.class) {
                totalCount++;
            }
            copyStream.write(result);
            count++;
        }
        return result;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] b, int off, int len)
            throws IOException {
        int length = in.read(b, off, len);
        if (length != -1) {
            synchronized (DebuggingInputStream.class) {
                totalCount += length;
            }
            copyStream.write(b, off, length);
            count += length;
        }
        return length;
    }

    public void close() throws IOException {
        super.close();
        copyStream.close();
    }
}
