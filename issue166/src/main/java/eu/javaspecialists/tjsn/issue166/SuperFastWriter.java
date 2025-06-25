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

public class SuperFastWriter {
    private static final long TERA_BYTE =
            1024L * 1024 * 1024 * 1024;

    public static void main(String... args) throws IOException {
        long bytesWritten = 0;
        byte[] data = new byte[100 * 1024 * 1024];
        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("bigdata.bin")
                )
        );
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10 * 1024 * 1024; i++) {
            out.writeObject(data);
            bytesWritten += data.length;
        }
        out.writeObject(null);
        out.close();
        time = System.currentTimeMillis() - time;
        System.out.printf("Wrote %d TB%n", bytesWritten / TERA_BYTE);
        System.out.println("time = " + time);
    }
}
