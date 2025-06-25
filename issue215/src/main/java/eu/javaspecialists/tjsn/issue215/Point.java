package eu.javaspecialists.tjsn.issue215;

public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized void move(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    public synchronized double distanceFromOrigin() {
        return Math.hypot(x, y);
    }

    public synchronized boolean moveIfAt(int oldX, int oldY,
                                         int newX, int newY) {
        if (x == oldX && y == oldY) {
            x = newX;
            y = newY;
            return true;
        } else {
            return false;
        }
    }
}
