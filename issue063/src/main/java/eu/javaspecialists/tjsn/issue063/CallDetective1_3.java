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

package eu.javaspecialists.tjsn.issue063;

/**
 * This is a pre-JDK 1.4 version of the CallDetective.
 * See TJSN 4th edition.
 */

import java.io.*;

public class CallDetective1_3 implements CallDetective {
    private final Throwable tracer = new Throwable();
    private final StringWriter sw = new StringWriter(1024);
    private final PrintWriter out = new PrintWriter(sw, false);

    public String findCaller(int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException();
        }

        int lineOfInterest = depth + 3;
        sw.getBuffer().setLength(0); // set the buffer back to zero
        tracer.fillInStackTrace();
        tracer.printStackTrace(out);

        LineNumberReader in = new LineNumberReader(
                new StringReader(sw.toString()));

        try {
            String result;
            while ((result = in.readLine()) != null) {
                if (in.getLineNumber() == lineOfInterest) {
                    return beautify(result);
                }
            }
        } catch (IOException ex) {
            // this should REALLY never happen
            throw new RuntimeException(ex.toString());
        }
        throw new IllegalArgumentException();
    }

    private static String beautify(String raw) {
        raw = raw.trim();
        if (raw.startsWith("at ")) {
            return raw.substring(3);
        }
        return raw;
    }
}
