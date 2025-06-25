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
