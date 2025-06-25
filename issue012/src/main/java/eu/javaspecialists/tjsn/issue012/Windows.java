package eu.javaspecialists.tjsn.issue012;

import java.awt.*;

public class Windows {
    public static void centerOnScreen(Window window) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(
                (d.width - window.getSize().width) / 2,
                (d.height - window.getSize().height) / 2);
    }
}