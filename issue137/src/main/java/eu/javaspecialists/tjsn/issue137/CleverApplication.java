package eu.javaspecialists.tjsn.issue137;

import java.util.logging.*;

public class CleverApplication {
    private static Logger logger = null;

    {logger = Logger.getLogger(this.getClass().getName());}

    public static void main(String[] args) {
        try {
            logger.info("static Hello");
        } catch (NullPointerException e) {
            System.out.println(
                    "failed to call Logger from static context");
        }
        CleverApplication m = new CleverApplication();
        m.run();
    }

    public void run() {
        logger.info("Hello");
    }
}
