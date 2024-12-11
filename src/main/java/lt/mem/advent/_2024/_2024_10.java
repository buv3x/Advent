package lt.mem.advent._2024;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2024_10 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/10.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<Point2D, Integer> grid = new HashMap<>();

    public static void first(List<String> input) {
        fillGrid(input);

        int total = 0;
        for (Point2D point2D : grid.keySet()) {
            Integer value = grid.get(point2D);
            if(value.equals(0)) {
                Set<Point2D> points = new HashSet<>();
                points.add(point2D);
                total += countPoints(points, 0);
            }
        }

        System.out.println(total);
    }

    public static void second(List<String> input) {
        fillGrid(input);

        int total = 0;
        for (Point2D point2D : grid.keySet()) {
            Integer value = grid.get(point2D);
            if(value.equals(0)) {
                Map<Point2D, Integer> points = new HashMap<>();
                points.put(point2D, 1);
                total += countPoints2(points, 0);
            }
        }

        System.out.println(total);

    }

    private static void fillGrid(List<String> input) {
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                Point2D point = new Point2D(j, i);
                grid.put(point, Integer.parseInt(input.get(i).charAt(j) + ""));
            }
        }
    }

    private static int countPoints2(Map<Point2D, Integer> points, int value) {
        if(value == 9) {
            int total = 0;
            for (Point2D point2D : points.keySet()) {
                total += points.get(point2D);
            }
            return total;
        }

        Map<Point2D, Integer> newPoints = new HashMap<>();
        for (Point2D point : points.keySet()) {
            List<Point2D> neighbours = PointsUtil.newPoints(point, Integer.MAX_VALUE, Integer.MAX_VALUE);
            for (Point2D neighbour : neighbours) {
                if(grid.containsKey(neighbour) && grid.get(neighbour).equals(value + 1)) {
                    if(!newPoints.containsKey(neighbour)) {
                        newPoints.put(neighbour, points.get(point));
                    } else {
                        newPoints.put(neighbour, points.get(point) + newPoints.get(neighbour));
                    }
                }
            }
        }

        return countPoints2(newPoints, value + 1);
    }

    private static int countPoints(Set<Point2D> points, int value) {
        if(value == 9) {
            return points.size();
        }

        Set<Point2D> newPoints = new HashSet<>();
        for (Point2D point : points) {
            List<Point2D> neighbours = PointsUtil.newPoints(point, Integer.MAX_VALUE, Integer.MAX_VALUE);
            for (Point2D neighbour : neighbours) {
                if(grid.containsKey(neighbour) && grid.get(neighbour).equals(value + 1)) {
                    newPoints.add(neighbour);
                }
            }
        }

        return countPoints(newPoints, value + 1);
    }




}
