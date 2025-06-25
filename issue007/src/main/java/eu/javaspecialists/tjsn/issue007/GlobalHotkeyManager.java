package eu.javaspecialists.tjsn.issue007;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GlobalHotkeyManager extends EventQueue {
    private static final boolean DEBUG = true; // BUG? what's that? ;-))
    private static final GlobalHotkeyManager instance =
            new GlobalHotkeyManager();
    private final InputMap keyStrokes = new InputMap();
    private final ActionMap actions = new ActionMap();

    static {
        // here we register ourselves as a new link in the chain of
        // responsibility
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(instance);
    }

    private GlobalHotkeyManager() {
    } // One is enough - singleton

    public static GlobalHotkeyManager getInstance() {
        return instance;
    }

    public InputMap getInputMap() {
        return keyStrokes;
    }

    public ActionMap getActionMap() {
        return actions;
    }

    protected void dispatchEvent(AWTEvent event) {
        if (event instanceof KeyEvent) {
            // KeyStroke.getKeyStrokeForEvent converts an ordinary KeyEvent
            // to a keystroke, as stored in the InputMap.  Keep in mind that
            // Numpad keystrokes are different to ordinary keys, i.e. if you
            // are listening to
            KeyStroke ks = KeyStroke.getKeyStrokeForEvent((KeyEvent) event);
            if (DEBUG) System.out.println("KeyStroke=" + ks);
            String actionKey = (String) keyStrokes.get(ks);
            if (actionKey != null) {
                if (DEBUG) System.out.println("ActionKey=" + actionKey);
                Action action = actions.get(actionKey);
                if (action != null && action.isEnabled()) {
                    // I'm not sure about the parameters
                    action.actionPerformed(
                            new ActionEvent(event.getSource(), event.getID(),
                                    actionKey, ((KeyEvent) event).getModifiers()));
                    return; // consume event
                }
            }
        }
        super.dispatchEvent(event); // let the next in chain handle event
    }
}