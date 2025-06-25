package eu.javaspecialists.tjsn.issue004;

import java.io.*;

/**
 * The stack trace starts with the following lines:
 * java.lang.Throwable
 * at StackTrace2.getStackTraceAtLine(StackTrace2.java:19)
 * <p>
 * It does not make sense to get these first 2 lines so we will
 * ignore them.  The number we pass into the method is the depth
 * of method calls, where 0 is the current line of code, 1 is the
 * line of code that called this code, 2 is the line of code that
 * called the line of code that called this code, etc.
 * <p>
 * 0:    at StackTrace2Test.g(StackTrace2Test.java:10)
 * 1:    at StackTrace2Test.f(StackTrace2Test.java:3)
 * 2:    at StackTrace2Test.main(StackTrace2Test.java:17)
 */
public class StackTrace2 {
    private static final Throwable tracer = new Throwable();
    private static final StringWriter sw =
            new StringWriter(1024);
    private static final PrintWriter out =
            new PrintWriter(sw, false);

    private StackTrace2() {
    } // Avoid direct creation

    public static String getCallStack(int depth) {
        synchronized (tracer) {
            if (depth < 0) throw new IllegalArgumentException();
            // skip the first 2 lines
            int lineOfInterest = depth + 3;
            // set the buffer back to zero
            sw.getBuffer().setLength(0);
            tracer.fillInStackTrace();
            tracer.printStackTrace(out);
            out.flush();
            LineNumberReader in = new LineNumberReader(
                    new StringReader(sw.toString()));
            try {
                String result;
                while ((result = in.readLine()) != null) {
                    if (in.getLineNumber() == lineOfInterest)
                        return beautify(result);
                }
            } catch (IOException ex) {} // we'll just return null
            return null;
        }
    }

    private static String beautify(String raw) {
        raw = raw.trim(); // we don't want any whitespace
        if (raw.startsWith("at ")) // we also cut off the "at "
            return raw.substring(3);
        return raw;
    }
}