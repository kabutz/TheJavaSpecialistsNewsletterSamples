package eu.javaspecialists.tjsn.issue019;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FrameLookup {
    private final Collection frames = new LinkedList();
    // Singleton Pattern
    private static final FrameLookup instance = new FrameLookup();

    public static FrameLookup getInstance() {
        return instance;
    }

    private FrameLookup() {
        Toolkit.getDefaultToolkit().addAWTEventListener(
                new AWTEventListener() {
                    public void eventDispatched(AWTEvent event) {
                        System.out.println("Event Dispatched : " + event);
                        if (event.getID() == WindowEvent.WINDOW_ACTIVATED) {
                            if (event.getSource() instanceof Frame) {
                                synchronized (frames) {
                                    frames.add(event.getSource());
                                }
                            }
                        }
                    }
                }, AWTEvent.WINDOW_EVENT_MASK);
    }

    public Frame lookupFrame(String title) {
        synchronized (frames) {
            Iterator it = frames.iterator();
            while (it.hasNext()) {
                Frame frame = (Frame) it.next();
                if (frame.getTitle().equals(title)) return frame;
            }
        }
        return null;
    }
}
