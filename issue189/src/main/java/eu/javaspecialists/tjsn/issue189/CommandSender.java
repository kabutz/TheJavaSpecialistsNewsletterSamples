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
