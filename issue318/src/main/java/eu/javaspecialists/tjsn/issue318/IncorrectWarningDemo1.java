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

package eu.javaspecialists.tjsn.issue318;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

public class IncorrectWarningDemo1 {
    public static void main(String... args) {
        try (var fc = FileChannel.open(Path.of("bla"));
             var flck = fc.lock()) { // or tryLock() ...
            // use try-with-resources to lock some file i/O ...
            // flck is not referenced in the try/catch/finally
            fc.write(ByteBuffer.allocate(10));
        } catch (IOException e) {
            // ...
        }
    }
}
