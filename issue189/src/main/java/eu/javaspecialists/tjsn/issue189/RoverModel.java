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

import lejos.pc.comm.*;

import java.io.*;

public class RoverModel {
    private int speed = 0;
    private final int SPEED_INCREMENT = 100;
    private final CommandSender sender;

    public RoverModel() throws IOException {
        System.out.println("Trying to connect to bluetooth NXT");
        NXTConnector conn = new NXTConnector();

        // Connect to any NXT over Bluetooth
        boolean connected = conn.connectTo("btspp://");

        if (!connected) {
            System.err.println("Failed to connect to any NXT");
            System.exit(1);
        }

        DataOutputStream out = conn.getDataOut();

        System.out.println("Made connection");

        sender = new CommandSender(out);
        sender.start();
    }

    public void backward() {
        speed -= SPEED_INCREMENT;
        if (speed < -900) {
            speed = -900;
        }
        sender.enqueueCommand(speed);
    }

    public void forward() {
        speed += SPEED_INCREMENT;
        if (speed > 900) {
            speed = 900;
        }
        sender.enqueueCommand(speed);
    }

    public void right() {
        sender.enqueueCommand(Protocol.RIGHT);
    }

    public void left() {
        sender.enqueueCommand(Protocol.LEFT);
    }

    public void blue() {
        sender.enqueueCommand(Protocol.BLUE);
    }

    public void red() {
        sender.enqueueCommand(Protocol.RED);
    }

    public void green() {
        sender.enqueueCommand(Protocol.GREEN);
    }

    public void noLights() {
        sender.enqueueCommand(Protocol.NO_LIGHT);
    }

    public void exit() throws InterruptedException {
        sender.enqueueCommand(Protocol.EXIT);
        System.out.println("Shutting down");
        Thread.sleep(1000);
    }
}
