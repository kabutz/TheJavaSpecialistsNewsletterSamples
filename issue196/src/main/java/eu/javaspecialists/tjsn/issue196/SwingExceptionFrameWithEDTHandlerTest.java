package eu.javaspecialists.tjsn.issue196;

import javax.swing.*;

public class SwingExceptionFrameWithEDTHandlerTest {
    public static void main(String... args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                Thread.currentThread().setUncaughtExceptionHandler(
                        new SwingExceptionHandler()
                );
            }
        });
        DemoFrame.create();
    }
}
