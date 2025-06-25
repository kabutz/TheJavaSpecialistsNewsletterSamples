package eu.javaspecialists.tjsn.issue113.take2;

import java.io.*;

public class Communication<E extends Enum<E> & EnumConverter<E>> {
    private final E enumSample;

    public Communication(E enumSample) {
        this.enumSample = enumSample;
    }

    public void sendOne(OutputStream out, E e) throws IOException {
        out.write(e.convert());
    }

    public E receiveOne(InputStream in) throws IOException {
        int b = in.read();
        if (b == -1) throw new EOFException();
        return enumSample.convert((byte) b);
    }
}
