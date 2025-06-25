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

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class CommandSender extends Thread {
    private final DataOutputStream out;
    private final BlockingQueue<Integer> commandQueue =
            new LinkedBlockingQueue<Integer>();

    public CommandSender(DataOutputStream out) {
        super("CommandSender");
        this.out = out;
    }

    public void run() {
        try {
            int data = 0;
            while (data != Protocol.EXIT) {
                data = commandQueue.take();
                sendData(data);
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    private void sendData(int data) {
        try {
            System.out.println("data = " + data);
            out.writeInt(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enqueueCommand(int data) {
        if (isSpeed(data)) {
            removePendingSpeedCommands();
        }
        commandQueue.add(data);
    }

    private void removePendingSpeedCommands() {
        Iterator<Integer> it = commandQueue.iterator();
        while (it.hasNext()) {
            int oldData = it.next();
            if (isSpeed(oldData)) {
                System.out.println("Removing " + oldData);
                it.remove();
            }
        }
    }

    private boolean isSpeed(int data) {
        return data > -1000 && data < 1000;
    }
}
