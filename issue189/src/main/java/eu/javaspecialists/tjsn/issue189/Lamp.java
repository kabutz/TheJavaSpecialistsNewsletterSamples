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

package eu.javaspecialists.tjsn.issue189;

import lejos.nxt.*;

public class Lamp {
    private final ColorLightSensor cs = new ColorLightSensor(
            SensorPort.S3, ColorLightSensor.TYPE_COLORNONE);
    private final Display display;

    public Lamp(Display display) {
        this.display = display;
    }

    public void noLight() {
        display.show("Light Off");
        cs.setType(ColorLightSensor.TYPE_COLORNONE);
    }

    public void blueLight() {
        display.show("Blue Light On");
        cs.setType(ColorLightSensor.TYPE_COLORBLUE);
    }

    public void redLight() {
        display.show("Red Light On");
        cs.setType(ColorLightSensor.TYPE_COLORRED);
    }

    public void greenLight() {
        display.show("Green Light On");
        cs.setType(ColorLightSensor.TYPE_COLORGREEN);
    }

    public void flashColors() {
        try {
            blueLight();
            Thread.sleep(300);
            redLight();
            Thread.sleep(300);
            greenLight();
            Thread.sleep(300);
        } catch (InterruptedException e) {
            // For an explanation why we should do this, please read
// https://www.javaspecialists.eu/archive/Issue146.html
            Thread.currentThread().interrupt();
        }
    }
}
