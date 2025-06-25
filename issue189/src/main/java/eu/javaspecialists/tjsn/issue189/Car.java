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
