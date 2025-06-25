package eu.javaspecialists.tjsn.issue137;

import java.util.logging.*;

public class Application {
    private static final Logger logger =
            Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        Application m = new Application();
        m.run();
    }

    public void run() {
        logger.info("Hello");
    }
}
