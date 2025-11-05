package lt.mem.advent._2016;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2016_13 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/13.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Point2D startPoint = new Point2D(1, 1);
    private static final Point2D endPoint = new Point2D(31,39);


    public static void first(List<String> lineInput) {
        int offset = Integer.parseInt(lineInput.get(0));
        Map<Point2D, Boolean> walls = new HashMap<>();
        Set<Point2D> toProcess = new HashSet<>();
        Set<Point2D> processedPoints = new HashSet<>();
        toProcess.add(startPoint);
        int steps = 0;
        while(!toProcess.contains(endPoint)) {
            Set<Point2D> newToProcess = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> newPoints = PointsUtil.newPoints(point, 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
                for (Point2D newPoint : newPoints) {
                    if(processedPoints.contains(newPoint)) {
                        continue;
                    }
                    if(walls.containsKey(newPoint)) {
                        if(walls.get(newPoint)) {
                            continue;
                        }
                    } else {
                        boolean wall = isWall(newPoint, offset);
                        walls.put(newPoint, wall);
                        if(wall) {
                            continue;
                        }
                    }

                    newToProcess.add(newPoint);
                }
            }

            processedPoints.addAll(toProcess);
            toProcess = newToProcess;
            steps++;
        }

        System.out.println(steps);
    }

    private static boolean isWall(Point2D point, int offset) {
        int value = offset + (point.getX() * point.getX()) + (3 * point.getX()) + (2 * point.getX() * point.getY())
                + point.getY() + (point.getY() * point.getY());
        String binaryString = Integer.toBinaryString(value);
        return (binaryString.length() - binaryString.replace("1", "").length()) % 2 != 0;
    }


    public static void second(List<String> lineInput) {
        int offset = Integer.parseInt(lineInput.get(0));
        Map<Point2D, Boolean> walls = new HashMap<>();
        Set<Point2D> toProcess = new HashSet<>();
        Set<Point2D> processedPoints = new HashSet<>();
        toProcess.add(startPoint);
        int steps = 0;
        while(steps <= 50) {
            Set<Point2D> newToProcess = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> newPoints = PointsUtil.newPoints(point, 0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
                for (Point2D newPoint : newPoints) {
                    if(processedPoints.contains(newPoint)) {
                        continue;
                    }
                    if(walls.containsKey(newPoint)) {
                        if(walls.get(newPoint)) {
                            continue;
                        }
                    } else {
                        boolean wall = isWall(newPoint, offset);
                        walls.put(newPoint, wall);
                        if(wall) {
                            continue;
                        }
                    }

                    newToProcess.add(newPoint);
                }
            }

            processedPoints.addAll(toProcess);
            toProcess = newToProcess;
            steps++;
        }

        System.out.println(processedPoints.size());
    }

}
