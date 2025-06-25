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

package eu.javaspecialists.tjsn.issue091;

import java.awt.*;
import java.awt.event.*;

public class MoveMouse implements RobotAction {
    private final int x;
    private final int y;

    public MoveMouse(Point to) {
        x = (int) to.getX();
        y = (int) to.getY();
    }

    public MoveMouse(MouseEvent event) {
        this(event.getPoint());
    }

    public Object execute(Robot robot) {
        robot.mouseMove(x, y);
        return null;
    }

    public String toString() {
        return "MoveMouse: x=" + x + ", y=" + y;
    }
}
