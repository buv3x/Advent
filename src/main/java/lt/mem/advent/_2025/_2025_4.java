package lt.mem.advent._2025;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2025_4 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/4.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        Set<Point2D> grid = parseGrid(input);
//        System.out.println(grid);

        int total = 0;
        for (Point2D point2D : grid) {
            int neighbours = 0;
            for (Direction direction : Direction.values()) {
                Point2D straightPoint = PointsUtil.newPointNoGrid(point2D, direction);
                if(grid.contains(straightPoint)) {
                    neighbours++;
                }
                Point2D diagonalPoint = PointsUtil.newPointNoGrid(straightPoint, PointsUtil.applyTurn(direction, Turn.RIGHT));
                if(grid.contains(diagonalPoint)) {
                    neighbours++;
                }
            }
            if(neighbours < 4) {
//                System.out.println(point2D);
                total++;
            }
        }
        System.out.println(total);
    }

    private static Set<Point2D> parseGrid(List<String> input) {
        Set<Point2D> grid = new LinkedHashSet<>();
        for(int i = 0; i < input.size(); ++i) {
            String row = input.get(i);
            for(int j = 0; j < row.length(); ++j) {
                if(row.charAt(j) == '@') {
                    grid.add(new Point2D(j, i));
                }
            }
        }
        return grid;
    }

    public static void second(List<String> input) {
        Set<Point2D> grid = parseGrid(input);
        Set<Point2D> toProcess = new HashSet<>(grid);
        while(true) {
            Set<Point2D> newToProcess = new HashSet<>();
            int total = 0;
            for (Point2D point2D : toProcess) {
                int neighbours = 0;
                for (Direction direction : Direction.values()) {
                    Point2D straightPoint = PointsUtil.newPointNoGrid(point2D, direction);
                    if(toProcess.contains(straightPoint)) {
                        neighbours++;
                    }
                    Point2D diagonalPoint = PointsUtil.newPointNoGrid(straightPoint, PointsUtil.applyTurn(direction, Turn.RIGHT));
                    if(toProcess.contains(diagonalPoint)) {
                        neighbours++;
                    }
                }
                if(neighbours < 4) {
                    total++;
                } else {
                    newToProcess.add(point2D);
                }
            }

            if(total == 0) {
                break;
            } else {
                toProcess = newToProcess;
            }
        }

        System.out.println(grid.size() - toProcess.size());
    }

}
