package lt.mem.advent._2016;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.List;
import java.util.Map;

public class _2016_2 {

    private static Map<Character, Direction> directionMap = Map.ofEntries(Map.entry('U', Direction.UP),
            Map.entry('D', Direction.DOWN), Map.entry('L', Direction.LEFT), Map.entry('R', Direction.RIGHT));

    private static Map<Point2D, Character> newPointsMap = Map.ofEntries(Map.entry(new Point2D(2, 0), '1'),
            Map.entry(new Point2D(1, 1), '2'), Map.entry(new Point2D(2, 1), '3'), Map.entry(new Point2D(3, 1), '4'),
            Map.entry(new Point2D(0, 2), '5'), Map.entry(new Point2D(1, 2), '6'),
            Map.entry(new Point2D(2, 2), '7'), Map.entry(new Point2D(3, 2), '8'), Map.entry(new Point2D(4, 2), '9'),
            Map.entry(new Point2D(1, 3), 'A'), Map.entry(new Point2D(2, 3), 'B'), Map.entry(new Point2D(3, 3), 'C'),
            Map.entry(new Point2D(2, 4), 'D'));

    private static Map<Point2D, Integer> pointsMap = Map.ofEntries(Map.entry(new Point2D(0, 0), 1),
            Map.entry(new Point2D(1, 0), 2), Map.entry(new Point2D(2, 0), 3), Map.entry(new Point2D(0, 1), 4),
            Map.entry(new Point2D(1, 1), 5), Map.entry(new Point2D(2, 1), 6),
            Map.entry(new Point2D(0, 2), 7), Map.entry(new Point2D(1, 2), 8), Map.entry(new Point2D(2, 2), 9));

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/2.txt");
//        first(lineInput);
        second(lineInput);
    }

    public static void first(List<String> lineInput) {
        Point2D currentPoint = new Point2D(1, 1);
        StringBuilder code = new StringBuilder();
        for (String s : lineInput) {
            for (char c : s.toCharArray()) {
                Direction direction = directionMap.get(c);
                Point2D nextPoint = PointsUtil.newPoint(currentPoint, direction, 2, 2);
                if(nextPoint != null) {
                    currentPoint = nextPoint;
                }
            }
            code.append(pointsMap.get(currentPoint));
        }
        System.out.println(code);
    }

    public static void second(List<String> lineInput) {
        Point2D currentPoint = new Point2D(0, 2);
        StringBuilder code = new StringBuilder();
        for (String s : lineInput) {
            for (char c : s.toCharArray()) {
                Direction direction = directionMap.get(c);
                Point2D nextPoint = PointsUtil.newPointNoGrid(currentPoint, direction);
                if(newPointsMap.containsKey(nextPoint)) {
                    currentPoint = nextPoint;
                }
            }
            code.append(newPointsMap.get(currentPoint));
        }
        System.out.println(code);

    }




}
