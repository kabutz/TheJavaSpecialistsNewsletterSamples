package eu.javaspecialists.tjsn.issue091;

import java.awt.*;
import java.io.*;
import java.net.*;

public class Student {
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Robot robot;

    public Student(String serverMachine, String studentName)
            throws IOException, AWTException {
        Socket socket = new Socket(serverMachine, Teacher.PORT);
        robot = new Robot();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(
                new BufferedInputStream(socket.getInputStream()));
        out.writeObject(studentName);
        out.flush();
    }

    public void run() throws ClassNotFoundException {
        try {
            while (true) {
                RobotAction action = (RobotAction) in.readObject();
                Object result = action.execute(robot);
                if (result != null) {
                    out.writeObject(result);
                    out.flush();
                    out.reset();
                }
            }
        } catch (IOException ex) {
            System.out.println("Connection closed");
        }
    }

    public static void main(String[] args) throws Exception {
        Student student = new Student(args[0], args[1]);
        student.run();
    }
}
