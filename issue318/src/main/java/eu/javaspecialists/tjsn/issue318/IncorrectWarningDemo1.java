package eu.javaspecialists.tjsn.issue318;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

public class IncorrectWarningDemo1 {
    public static void main(String... args) {
        try (var fc = FileChannel.open(Path.of("bla"));
             var flck = fc.lock()) { // or tryLock() ...
            // use try-with-resources to lock some file i/O ...
            // flck is not referenced in the try/catch/finally
            fc.write(ByteBuffer.allocate(10));
        } catch (IOException e) {
            // ...
        }
    }
}
