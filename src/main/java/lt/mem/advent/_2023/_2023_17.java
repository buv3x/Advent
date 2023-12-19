package lt.mem.advent._2023;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class _2023_17 {

    private static List<List<Cell>> cells = new ArrayList<>();
    private static int MIN_HEAT = Integer.MAX_VALUE;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/17.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        process(input, 1, 3);
    }

    public static void second(List<String> input) {
        process(input, 4, 10);
    }

    private static void process(List<String> input, int minStraight, int maxStraight) {
        for (String line : input) {
            List<Cell> cellRow = new ArrayList<>();
            cells.add(cellRow);
            for (char character : line.toCharArray()) {
                cellRow.add(new Cell(Integer.parseInt(character + "")));
            }
        }

        List<DirectionRunPoint> toProcess = new ArrayList<>();
        toProcess.add(new DirectionRunPoint(new Point2D(1, 0), Direction.RIGHT, 0, 1));
        toProcess.add(new DirectionRunPoint(new Point2D(0, 1), Direction.DOWN, 0, 1));
        while(CollectionUtils.isNotEmpty(toProcess)) {
            List<DirectionRunPoint> newPoints = new ArrayList<>();
            for (DirectionRunPoint directionRunPoint : toProcess) {
                newPoints.addAll(process(directionRunPoint, minStraight, maxStraight));
            }
            toProcess = newPoints;
        }

        System.out.println(MIN_HEAT);
    }

    private static List<DirectionRunPoint> process(DirectionRunPoint directionRunPoint, int minStraight, int maxStraight) {
        Cell cell = cellByPoint(directionRunPoint.point);
        int newValue = directionRunPoint.value + cell.value;
        if(newValue > MIN_HEAT) {
            return Collections.emptyList();
        }
        if(directionRunPoint.point.equals(new Point2D(cells.get(0).size() - 1, cells.size() - 1))) {
            if(directionRunPoint.run >= minStraight) {
                MIN_HEAT = newValue;
            }
            return Collections.emptyList();
        }

        Map<Direction, Map<Integer, Integer>> cache = cell.cache;
        if(cell.cache.containsKey(directionRunPoint.direction)) {
            Map<Integer, Integer> runsMap = cache.get(directionRunPoint.direction);
            if(runsMap.containsKey(directionRunPoint.run) && runsMap.get(directionRunPoint.run) <= newValue) {
                return Collections.emptyList();
            }
        }

        if(!cell.cache.containsKey(directionRunPoint.direction)) {
            cell.cache.put(directionRunPoint.direction, new HashMap<>());
        }
        cache.get(directionRunPoint.direction).put(directionRunPoint.run, newValue);

        List<DirectionRunPoint> newPoints = new ArrayList<>();

        int maxX = cells.get(0).size() - 1;
        int maxY = cells.size() - 1;
        if(directionRunPoint.run >= minStraight) {
            Direction leftTurn = PointsUtil.applyTurn(directionRunPoint.direction, Turn.LEFT);
            Point2D leftPoint = PointsUtil.newPoint(directionRunPoint.point, leftTurn, maxX, maxY);
            if (leftPoint != null) {
                newPoints.add(new DirectionRunPoint(leftPoint, leftTurn, newValue, 1));
            }
        }

        if(directionRunPoint.run >= minStraight) {
            Direction rightTurn = PointsUtil.applyTurn(directionRunPoint.direction, Turn.RIGHT);
            Point2D rightPoint = PointsUtil.newPoint(directionRunPoint.point, rightTurn, maxX, maxY);
            if (rightPoint != null) {
                newPoints.add(new DirectionRunPoint(rightPoint, rightTurn, newValue, 1));
            }
        }

        if(directionRunPoint.run < maxStraight) {
            Point2D straightPoint = PointsUtil.newPoint(directionRunPoint.point, directionRunPoint.direction, maxX, maxY);
            if(straightPoint != null) {
                newPoints.add(new DirectionRunPoint(straightPoint, directionRunPoint.direction, newValue, directionRunPoint.run + 1));
            }
        }

        return newPoints;
    }

    private static Cell cellByPoint(Point2D point) {
        return cells.get(point.getY()).get(point.getX());
    }

    private static class Cell {

        int value;

        Map<Direction, Map<Integer, Integer>> cache = new HashMap<>();

        public Cell(int value) {
            this.value = value;
        }
    }

    private static class DirectionRunPoint {

        Point2D point;
        Direction direction;
        int value;
        int run;

        public DirectionRunPoint(Point2D point, Direction direction, int value, int run) {
            this.point = point;
            this.direction = direction;
            this.value = value;
            this.run = run;
        }
    }

}
