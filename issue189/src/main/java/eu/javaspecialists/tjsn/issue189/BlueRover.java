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

package eu.javaspecialists.tjsn.issue189;

import lejos.nxt.comm.*;

import java.io.*;

public class BlueRover {
    public static void main(String... args) throws Exception {
        new BlueRover().start();
    }

    private final Display display = new Display();
    private final Car rover = new Car(display);
    private final Lamp lamp = new Lamp(display);

    private void start() {
        try {
            display.show("Waiting...");
            NXTConnection con = Bluetooth.waitForConnection();
            display.show("Connected");

            DataInputStream dis = con.openDataInputStream();

            while (processCommand(dis.readInt())) ;

            rover.stop();
            lamp.flashColors();
            display.show("Disconnected");
        } catch (Throwable t) {
            display.show(t.toString());
        }
    }

    private boolean processCommand(int data) {
        if (data == Protocol.EXIT) {
            return false;
        }

        // I don't like this either, but I wanted to have as little
// code and as few classes as possible.  Once we add a state
// machine onto the BlueRover, we will refactor this code.
        if (data == Protocol.STOP) {
            rover.stop();
        } else if (data == Protocol.RIGHT) {
            rover.turnRight();
        } else if (data == Protocol.LEFT) {
            rover.turnLeft();
        } else if (data == Protocol.BLUE) {
            lamp.blueLight();
        } else if (data == Protocol.RED) {
            lamp.redLight();
        } else if (data == Protocol.GREEN) {
            lamp.greenLight();
        } else if (data == Protocol.NO_LIGHT) {
            lamp.noLight();
        } else if (data < 0) {
            rover.reverse(-data);
        } else if (data > 0) {
            rover.forward(data);
        }
        return true;
    }
}
