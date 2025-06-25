package eu.javaspecialists.tjsn.issue137;

import java.util.logging.*;

public class BetterApplication {
    private static final Logger logger = LoggerFactory.make();

    public static void main(String[] args) {
        BetterApplication m = new BetterApplication();
        m.run();
    }

    public void run() {
        logger.info("Hello");
    }
}
