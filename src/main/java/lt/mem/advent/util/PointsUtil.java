package lt.mem.advent.util;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public static Point2D newPointNoGrid(Point2D point, Direction direction) {
        return newPoint(point, direction, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static List<Point2D> newPoints(Point2D point, int minX, int minY, int maxX, int maxY) {
        List<Point2D> newPoints = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Point2D newPoint = newPoint(point, direction, minX, minY, maxX, maxY);
            if(newPoint != null) {
                newPoints.add(newPoint);
            }
        }
        return newPoints;
    }

    public static List<Point2D> newPoints(Point2D point, int maxX, int maxY) {
        return newPoints(point, 0, 0, maxX, maxY);
    }

    public static List<Point2D> newPointsNoGrid(Point2D point) {
        return newPoints(point, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public static Direction applyTurn(Direction direction, Turn turn) {
        return Direction.of((direction.getValue() + turn.getValue()) % 4);
    }

    public static boolean isInGrid(Point2D point, int maxX, int maxY) {
        return isInGrid(point, 0, 0, maxX, maxY);
    }

    public static boolean isInGrid(Point2D point, int minX, int minY, int maxX, int maxY) {
        return point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY;
    }

    public static Pair<Point2D, Point2D> getAntipodes(Point2D first, Point2D second) {
        return Pair.of(
                new Point2D(first.getX() * 2 - second.getX(), first.getY() * 2 - second.getY()),
                new Point2D(second.getX() * 2 - first.getX(), second.getY() * 2 - first.getY())
        );
    }

    public static List<Point2D> getPointsOnLine(Point2D first, Point2D second, int maxX, int maxY) {
        return getPointsOnLine(first, second, 0, 0, maxX, maxY);
    }

    public static List<Point2D> getPointsOnLine(Point2D first, Point2D second, int minX, int minY, int maxX, int maxY) {
        int i = 0;
        List<Point2D> points = new ArrayList<>();
        while(true) {
            Point2D newPoint = new Point2D(first.getX() + i * (first.getX() - second.getX()),
                    first.getY() + i * (first.getY() - second.getY()));
            if(!isInGrid(newPoint, minX, minY, maxX, maxY)) {
                break;
            }
            points.add(newPoint);
            i++;
        }

        i = 0;
        while(true) {
            Point2D newPoint = new Point2D(second.getX() + i * (second.getX() - first.getX()),
                    second.getY() + i * (second.getY() - first.getY()));
            if(!isInGrid(newPoint, minX, minY, maxX, maxY)) {
                break;
            }
            points.add(newPoint);
            i++;
        }

        return points;
    }

    public static void printWalls(Set<Point2D> walls, int maxX, int maxY) {
        for(int i = 0; i <= maxY; ++i) {
            for(int j = 0; j <= maxX; ++j) {
                System.out.print(walls.contains(new Point2D(j, i)) ? '#' : '.');
            }
            System.out.println();
        }
    }


}
