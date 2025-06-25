package eu.javaspecialists.tjsn.issue196;

public class SwingExceptionFrameWithDefaultHandlerTest {
    public static void main(String... args) {
        Thread.setDefaultUncaughtExceptionHandler(
                new SwingExceptionHandler());

        DemoFrame.create();
    }
}
