package eu.javaspecialists.tjsn.issue137;

import java.util.logging.*;

public class LoggerFactory {
    public static Logger make() {
        Throwable t = new Throwable();
        StackTraceElement directCaller = t.getStackTrace()[1];
        return Logger.getLogger(directCaller.getClassName());
    }
}
