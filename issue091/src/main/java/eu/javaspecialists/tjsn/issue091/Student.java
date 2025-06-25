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
