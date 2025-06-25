package eu.javaspecialists.tjsn.issue097;

import org.dom4j.*;

import javax.swing.*;

public class ComponentSlider {
    @FromXml
    private boolean inverted = false;
    @FromXml
    private int min = Integer.MIN_VALUE;
    @FromXml
    private int max = Integer.MIN_VALUE;
    @FromXml
    private int minorTickInterval = Integer.MIN_VALUE;
    @FromXml
    private int majorTickInterval = Integer.MIN_VALUE;
    @FromXml
    private boolean snapToTick = false;

    public ComponentSlider(JSlider slider, Element def) {
        XmlConstructor.constructFromXml(this, def);
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setInverted(inverted);
        if (minorTickInterval != Integer.MIN_VALUE) {
            slider.setMinorTickSpacing(minorTickInterval);
            slider.setPaintTicks(true);
            slider.setSnapToTicks(snapToTick);
        }
        if (majorTickInterval != Integer.MIN_VALUE) {
            slider.setMajorTickSpacing(majorTickInterval);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setSnapToTicks(snapToTick);
        }
    }
}
