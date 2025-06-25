package eu.javaspecialists.tjsn.issue182;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;

public class TeacherServer {
    public static final int PORT = 5555;

    private final SocketWriterThread writer;
    private final TeacherFrame frame;
    private final SocketReaderThread reader;

    public TeacherServer(Socket socket)
            throws IOException, ClassNotFoundException,
            InvocationTargetException, InterruptedException {
        ObjectOutputStream out = new ObjectOutputStream(
                socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        socket.getInputStream()));
        System.out.println("waiting for student name ...");
        final String studentName = (String) in.readObject();

        reader = new SocketReaderThread(studentName, in, this);
        writer = new SocketWriterThread(studentName, out);

        final TeacherFrame[] temp = new TeacherFrame[1];
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                temp[0] = new TeacherFrame(studentName,
                        TeacherServer.this, writer);
            }
        });
        frame = temp[0];

        reader.start();
        writer.start();

        System.out.println("finished connecting to " + socket);
    }

    public void showScreenShot(byte[] bytes) throws IOException {
        frame.showScreenShot(bytes);
    }

    public void shutdown() {
        writer.interrupt();
        reader.close();
    }

    public static void main(String... args) throws Exception {
        ServerSocket ss = new ServerSocket(PORT);
        while (true) {
            Socket socket = ss.accept();
            System.out.println("Connection From " + socket);
            new TeacherServer(socket);
        }
    }
}
