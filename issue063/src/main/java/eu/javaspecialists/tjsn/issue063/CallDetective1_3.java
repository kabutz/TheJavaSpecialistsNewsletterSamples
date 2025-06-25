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
