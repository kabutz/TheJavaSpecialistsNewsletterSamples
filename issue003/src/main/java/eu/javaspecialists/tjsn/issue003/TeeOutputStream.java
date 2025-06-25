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

package eu.javaspecialists.tjsn.issue003;

// TeeOutputStream

import java.io.*;

public class TeeOutputStream extends FilterOutputStream {
    private final OutputStream out2;

    public TeeOutputStream(OutputStream out1, OutputStream out2) {
        super(out1);
        this.out2 = out2;
    }

    public void write(int b) throws IOException {
        super.write(b);
        out2.write(b);
    }

    public void flush() throws IOException {
        super.flush();
        out2.flush();
    }

    public void close() throws IOException {
        super.close();
        out2.close();
    }
}
