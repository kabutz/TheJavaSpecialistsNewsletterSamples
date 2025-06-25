package eu.javaspecialists.tjsn.issue033;

import java.io.*;

public class ExceptionConverterTest {
    private static void f() throws IOException {
        throw new IOException("File broken");
    }

    private static void g() {
        try {
            f();
        } catch (IOException ex) {
            System.out.println("Printing out plain ol' IOException:");
            System.out.println("---");
            ex.printStackTrace();
            System.out.println("---");
            throw new ExceptionConverter(ex);
        }
    }

    public static void main(String args[]) {
        try {
            g();
        } catch (RuntimeException ex) {
            System.out.println("Printing out RuntimeException:");
            System.out.println("---");
            ex.printStackTrace();
            System.out.println("---");
            System.out.println("That's it!");
        }
    }
}
