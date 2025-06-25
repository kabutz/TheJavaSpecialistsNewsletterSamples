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

package eu.javaspecialists.tjsn.issue059;

import java.io.*;

public class ConstantPoolSize {
    static void skip(InputStream in, int skip) throws IOException {
        while (skip > 0) {
            skip -= in.skip(skip);
        }
    }

    public static void main(String[] args) throws IOException {
        DataInputStream din = new DataInputStream(
                new FileInputStream(args[0]));
        skip(din, 8);
        int count = ((din.readByte() & 0xff) << 8)
                | (din.readByte() & 0xff);
        System.out.println(count);
        din.close();
    }
}
