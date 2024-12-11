package lt.mem.advent._2024;

import lt.mem.advent.structure.DirectedPoint2D;
import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2024_6 {


    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/6.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<Point2D, Boolean> grid = new HashMap<>();

    public static void first(List<String> input) {
        Point2D current = fillGrid(input);

        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;
        Direction direction = Direction.UP;

        Set<Point2D> visitedGrid = new HashSet<>();

        while(current != null) {
            visitedGrid.add(current);
            Point2D newPoint = PointsUtil.newPoint(current, direction, maxX, maxY);
            if(newPoint != null) {
                if(grid.get(newPoint)) {
                    direction = PointsUtil.applyTurn(direction, Turn.RIGHT);
                }
                current = PointsUtil.newPoint(current, direction, maxX, maxY);
            } else {
                current = null;
            }
        }

        System.out.println(visitedGrid.size());
    }

    public static void second(List<String> input) {
        Point2D startPoint = fillGrid(input);

        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;

        Set<Point2D> visitedGrid = new HashSet<>();
        Point2D currentPoint = startPoint;
        Direction direction = Direction.UP;
        while(currentPoint != null) {
            visitedGrid.add(currentPoint);
            Point2D newPoint = PointsUtil.newPoint(currentPoint, direction, maxX, maxY);
            if(newPoint != null) {
                if(grid.get(newPoint)) {
                    direction = PointsUtil.applyTurn(direction, Turn.RIGHT);
                }
                currentPoint = PointsUtil.newPoint(currentPoint, direction, maxX, maxY);
            } else {
                currentPoint = null;
            }
        }

        System.out.println(visitedGrid.size());

        int total = 0;
        for (Point2D gridPoint : visitedGrid) {
            if(!gridPoint.equals(startPoint)) {
                currentPoint = startPoint;
                direction = Direction.UP;
                Set<DirectedPoint2D> visitedDirectedGrid = new HashSet<>();

                while(true) {
                    DirectedPoint2D directedPoint = new DirectedPoint2D(currentPoint, direction);
                    if(visitedDirectedGrid.contains(directedPoint)) {
//                        System.out.println(gridPoint);
                        total++;
                        break;
                    }
                    visitedDirectedGrid.add(directedPoint);
                    Point2D newPoint = PointsUtil.newPoint(currentPoint, direction, maxX, maxY);
                    if(newPoint != null) {
                        if(newPoint.equals(gridPoint) || grid.get(newPoint)) {
                            direction = PointsUtil.applyTurn(direction, Turn.RIGHT);
                        } else {
                            currentPoint = PointsUtil.newPoint(currentPoint, direction, maxX, maxY);
                        }
                    } else {
                        currentPoint = null;
                    }

                    if(currentPoint == null) {
                        break;
                    }
                }
            }
        }

        System.out.println(total);
    }

    private static Point2D fillGrid(List<String> input) {
        Point2D current = null;
        for (int i = 0; i < input.size(); ++i) {
            for (int j = 0; j < input.get(i).length(); ++j) {
                Point2D point = new Point2D(j, i);
                char c = input.get(i).charAt(j);
                if(c == '#') {
                    grid.put(point, true);
                } else {
                    grid.put(point, false);
                }
                if(c == '^') {
                    current = point;
                }
            }
        }
        return current;
    }

}
