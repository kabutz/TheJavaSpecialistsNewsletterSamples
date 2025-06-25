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
import java.util.*;

public class ModifiedObjectWriter {
    public static void main(String... args) throws IOException {
        byte[] data = new byte[10 * 1024 * 1024];
        ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream("smalldata.bin")
                )
        );
        for (int i = -128; i < 128; i++) {
            Arrays.fill(data, (byte) i);
            out.writeObject(data);
        }
        out.writeObject(null);
        out.close();
    }
}
