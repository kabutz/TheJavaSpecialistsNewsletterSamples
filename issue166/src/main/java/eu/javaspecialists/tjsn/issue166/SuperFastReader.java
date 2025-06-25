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

package eu.javaspecialists.tjsn.issue166;

import java.io.*;

public class SuperFastReader {
    private static final long TERA_BYTE =
            1024L * 1024 * 1024 * 1024;

    public static void main(String... args) throws Exception {
        long bytesRead = 0;
        ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream("bigdata.bin")
                )
        );
        long time = System.currentTimeMillis();
        byte[] data;
        while ((data = (byte[]) in.readObject()) != null) {
            bytesRead += data.length;
        }
        in.close();
        time = System.currentTimeMillis() - time;
        System.out.printf("Read %d TB%n", bytesRead / TERA_BYTE);
        System.out.println("time = " + time);
    }
}
