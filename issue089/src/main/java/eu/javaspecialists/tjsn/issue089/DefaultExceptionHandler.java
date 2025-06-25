package eu.javaspecialists.tjsn.issue089;

import javax.swing.*;
import java.awt.*;
import java.lang.Thread.*;

public class DefaultExceptionHandler implements UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        // Here you should have a more robust, permanent record of problems
        JOptionPane.showMessageDialog(findActiveFrame(),
                e.toString(), "Exception Occurred", JOptionPane.OK_OPTION);
        e.printStackTrace();
    }

    private Frame findActiveFrame() {
        Frame[] frames = JFrame.getFrames();
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isVisible()) {
                return frames[i];
            }
        }
        return null;
    }
}
