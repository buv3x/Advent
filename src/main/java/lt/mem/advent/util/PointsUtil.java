package lt.mem.advent.util;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;

public class PointsUtil {

    public static int squareDistance(Point2D p1, Point2D p2) {
        return Math.max(p1.getX(), p2.getX()) - Math.min(p1.getX(), p2.getX()) +
                Math.max(p1.getY(), p2.getY()) - Math.min(p1.getY(), p2.getY());
    }

    public static Point2D newPoint(Point2D point, Direction direction, int minX, int minY, int maxX, int maxY) {
        switch (direction) {
            case UP: {
                return point.getY() > minY ? new Point2D(point.getX(), point.getY() - 1) : null;
            }
            case DOWN: {
                return point.getY() < maxY ? new Point2D(point.getX(), point.getY() + 1) : null;
            }
            case LEFT: {
                return point.getX() > minX ? new Point2D(point.getX() - 1, point.getY()) : null;
            }
            case RIGHT: {
                return point.getX() < maxX ? new Point2D(point.getX() + 1, point.getY()) : null;
            }
        }
        return null;
    }

    public static Point2D newPoint(Point2D point, Direction direction, int maxX, int maxY) {
        return newPoint(point, direction, 0, 0, maxX, maxY);
    }

    public static Direction applyTurn(Direction direction, Turn turn) {
        return Direction.of((direction.getValue() + turn.getValue()) % 4);

    }
}
