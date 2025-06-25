package eu.javaspecialists.tjsn.issue182;

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

    public boolean equals(Object o) {
        if (!(o instanceof MoveMouse)) return false;
        MoveMouse mm = (MoveMouse) o;
        return x == mm.x && y == mm.y;
    }

    public int hashCode() {
        return (x << 16) + y;
    }
}
