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

package eu.javaspecialists.tjsn.issue056;

import java.io.*;

public class BlockedOnIO extends Thread {
    private final InputStream in;

    public BlockedOnIO(InputStream in) {
        this.in = in;
    }

    public void interrupt() {
        super.interrupt();
        try {
            in.close();
        } catch (IOException e) {} // quietly close
    }

    public void run() {
        try {
            System.out.println("Reading from input stream");
            in.read();
            System.out.println("Finished reading");
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted via InterruptedIOException");
        } catch (IOException e) {
            if (!isInterrupted()) {
                e.printStackTrace();
            } else {
                System.out.println("Interrupted");
            }
        }
        System.out.println("Shutting down thread");
    }
}
