/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
