package eu.javaspecialists.tjsn.issue148;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;

public class SliderSnap extends BasicSliderUI {
    /**
     * The UI class implements the current slider Look and Feel.
     */
    private static Class sliderClass;
    private static Method xForVal, yForVal;
    private static ReinitListener reinitListener =
            new ReinitListener();

    public SliderSnap() {
        super(null);
    }

    /**
     * Returns the UI as normal, but intercepts the call, so a
     * listener can be attached.
     */
    public static ComponentUI createUI(JComponent c) {
        if (c == null || sliderClass == null)
            return null;
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        try {
            Method m = (Method) defaults.get(sliderClass);
            if (m == null) {
                m = sliderClass.getMethod("createUI",
                        new Class[]{JComponent.class});
                defaults.put(sliderClass, m);
            }
            ComponentUI uiObject = (ComponentUI) m.invoke(null,
                    new Object[]{c});
            if (uiObject instanceof BasicSliderUI)
                c.addHierarchyListener(new MouseAttacher());
            return uiObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        //check we don't initialise twice
        if (sliderClass != null)
            return;
        Init init = new Init();
        if (EventQueue.isDispatchThread()) {
            init.run();
        } else {
            // This code must run on the EDT for data visibility
            try {
                EventQueue.invokeAndWait(init);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Listeners for when the JSlider becomes visible then
     * attaches the mouse listeners, then removes itself.
     */
    private static class MouseAttacher implements HierarchyListener {
        public void hierarchyChanged(HierarchyEvent evt) {
            long flags = evt.getChangeFlags();
            if ((flags & HierarchyEvent.DISPLAYABILITY_CHANGED) > 0
                    && evt.getComponent() instanceof JSlider) {
                JSlider c = (JSlider) evt.getComponent();
                c.removeHierarchyListener(this);
                attachTo(c);
            }
        }
    }

    /**
     * Listens for Look and Feel changes and re-initialises the
     * class.
     */
    private static class ReinitListener implements
            PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if ("lookAndFeel".equals(evt.getPropertyName())) {
                // The look and feel was changed so we need to re-insert
// Our hook into the new UIDefaults map
                sliderClass = null;
                xForVal = yForVal = null;
                UIManager.removePropertyChangeListener(reinitListener);
                init();
            }
        }
    }

    /**
     * Initialises the reflective methods and adjusts the current
     * Look and Feel.
     */
    private static class Init implements Runnable {
        public void run() {
            try {
                UIDefaults defaults = UIManager.getLookAndFeelDefaults();
                sliderClass = defaults.getUIClass("SliderUI");
                // Set up two reflective method calls
                xForVal = BasicSliderUI.class.getDeclaredMethod(
                        "xPositionForValue",
                        new Class[]{int.class});
                yForVal = BasicSliderUI.class.getDeclaredMethod(
                        "yPositionForValue",
                        new Class[]{int.class});
                // Allow us access to the methods
                xForVal.setAccessible(true);
                yForVal.setAccessible(true);
                // Replace UI class with ourselves
                defaults.put("SliderUI", SliderSnap.class.getName());
                UIManager.addPropertyChangeListener(reinitListener);
            } catch (Exception e) {
                sliderClass = null;
                xForVal = yForVal = null;
            }
        }
    }

    /**
     * Called to attach mouse listeners to the JSlider.
     */
    private static void attachTo(JSlider c) {
        MouseMotionListener[] listeners = c.getMouseMotionListeners();
        for (int i = 0; i < listeners.length; i++) {
            MouseMotionListener m = listeners[i];
            if (m instanceof TrackListener) {
                c.removeMouseMotionListener(m); //remove original
                SnapListener listen = new SnapListener(m,
                        (BasicSliderUI) c.getUI(), c);
                c.addMouseMotionListener(listen);
                c.addMouseListener(listen);
                c.addPropertyChangeListener("UI", listen);
            }
        }
    }

    private static class SnapListener extends MouseInputAdapter
            implements PropertyChangeListener {
        private MouseMotionListener delegate;
        /**
         * Original Look and Feel implementation
         */
        private BasicSliderUI ui;
        /**
         * Our slider
         */
        private JSlider slider;
        /**
         * Offset of mouse click from centre of slider thumb
         */
        private int offset;

        public SnapListener(MouseMotionListener delegate,
                            BasicSliderUI ui, JSlider slider) {
            this.delegate = delegate;
            this.ui = ui;
            this.slider = slider;
        }

        /**
         * UI can change at any point, so we need to listen for these
         * events.
         */
        public void propertyChange(PropertyChangeEvent evt) {
            if ("UI".equals(evt.getPropertyName())) {
                // Remove old listeners and create new ones
                slider.removeMouseMotionListener(this);
                slider.removeMouseListener(this);
                slider.removePropertyChangeListener("UI", this);
                attachTo(slider);
            }
        }

        /**
         * Implements the actual "snap while dragging" behaviour.  If
         * snap to ticks is enabled on this slider, then the location
         * for the nearest tick/label is calculated and the click
         * location is translated before being passed to the
         * delegate.
         */
        public void mouseDragged(MouseEvent evt) {
            if (slider.getSnapToTicks()) { // if we are set to snap
                int pos = getLocationForValue(getSnappedValue(evt));
                // if above call fails and returns -1, take no action
                if (pos > -1) {
                    if (slider.getOrientation() == JSlider.HORIZONTAL)
                        evt.translatePoint(pos - evt.getX() + offset, 0);
                    else
                        evt.translatePoint(0, pos - evt.getY() + offset);
                }
            }
            delegate.mouseDragged(evt);
        }

        /**
         * When the slider is clicked we need to record the offset
         * from thumb center.
         */
        public void mousePressed(MouseEvent evt) {
            int pos = (slider.getOrientation() == JSlider.HORIZONTAL) ?
                    evt.getX() : evt.getY();
            int loc = getLocationForValue(getSnappedValue(evt));
            this.offset = (loc < 0) ? 0 : pos - loc;
        }

        /* Pass straight to delegate. */
        public void mouseMoved(MouseEvent evt) {
            delegate.mouseMoved(evt);
        }

        /**
         * Calculates the nearest snapable value given a MouseEvent.
         * Code adapted from BasicSliderUI.
         */
        public int getSnappedValue(MouseEvent evt) {
            int value = slider.getOrientation() == JSlider.HORIZONTAL
                    ? ui.valueForXPosition(evt.getX())
                    : ui.valueForYPosition(evt.getY());
            // Now calculate if we should adjust the value
            int snappedValue = value;
            int tickSpacing = 0;
            int majorTickSpacing = slider.getMajorTickSpacing();
            int minorTickSpacing = slider.getMinorTickSpacing();
            if (minorTickSpacing > 0)
                tickSpacing = minorTickSpacing;
            else if (majorTickSpacing > 0)
                tickSpacing = majorTickSpacing;
            // If it's not on a tick, change the value
            if (tickSpacing != 0) {
                if ((value - slider.getMinimum()) % tickSpacing != 0) {
                    float temp = (float) (value - slider.getMinimum())
                            / (float) tickSpacing;
                    snappedValue = slider.getMinimum() +
                            (Math.round(temp) * tickSpacing);
                }
            }
            return snappedValue;
        }

        /**
         * Provides the x or y co-ordinate for a slider value,
         * depending on orientation.
         */
        public int getLocationForValue(int value) {
            try {
                // Reflectively call slider ui code
                Method m = slider.getOrientation() == JSlider.HORIZONTAL
                        ? xForVal : yForVal;
                Integer result = (Integer) m.invoke(
                        ui, new Object[]{new Integer(value)});
                return result.intValue();
            } catch (InvocationTargetException e) {
                return -1;
            } catch (IllegalAccessException e) {
                return -1;
            }
        }
    }
}
