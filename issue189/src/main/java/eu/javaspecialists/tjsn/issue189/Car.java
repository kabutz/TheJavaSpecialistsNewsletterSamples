package eu.javaspecialists.tjsn.issue189;

import lejos.nxt.*;

public class Car {
    private final Motor rightServo = Motor.B;
    private final Motor leftServo = Motor.C;
    private final Display display;

    public Car(Display display) {
        this.display = display;
    }

    public void forward(int newSpeed) {
        display.show("Forward " + newSpeed);
        setSpeed(newSpeed);
        rightServo.forward();
        leftServo.forward();
    }

    public void reverse(int newSpeed) {
        display.show("Reverse " + newSpeed);
        setSpeed(newSpeed);
        rightServo.backward();
        leftServo.backward();
    }

    public void setSpeed(int speed) {
        rightServo.setSpeed(speed);
        leftServo.setSpeed(speed);
    }

    public void turnRight() {
        display.show("Turn Right");
        rightServo.backward();
        leftServo.forward();
    }

    public void turnLeft() {
        display.show("Turn Left");
        rightServo.forward();
        leftServo.backward();
    }

    public void stop() {
        display.show("Stop");
        rightServo.stop();
        leftServo.stop();
    }
}
