package lt.mem.advent._2016;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class _2016_1 {

    public static void main(String[] args) {
        String input = ReaderUtil.readInput("_2016/1.txt");
//        first(input);
        second(input);
    }

    public static void first(String input) {
        String[] split = StringUtils.split(input, ", ");
        Point2D point = new Point2D(0, 0);
        Direction direction = Direction.UP;
        for (String s : split) {
            char directionChange = s.charAt(0);
            if(directionChange == 'R') {
                direction = PointsUtil.applyTurn(direction, Turn.RIGHT);
            } else {
                direction = PointsUtil.applyTurn(direction, Turn.LEFT);
            }

            int steps = Integer.parseInt(s.substring(1));
            for (int i = 0; i < steps; ++i) {
                point = PointsUtil.newPointNoGrid(point, direction);
            }
        }

        System.out.println(Math.abs(point.getX()) + Math.abs(point.getY()));

    }

    public static void second(String input) {
        String[] split = StringUtils.split(input, ", ");
        Point2D point = new Point2D(0, 0);
        Direction direction = Direction.UP;
        Set<Point2D> visitedPoints = new HashSet<>();
        visitedPoints.add(point);
        outer: for (String s : split) {
            char directionChange = s.charAt(0);
            if(directionChange == 'R') {
                direction = PointsUtil.applyTurn(direction, Turn.RIGHT);
            } else {
                direction = PointsUtil.applyTurn(direction, Turn.LEFT);
            }

            int steps = Integer.parseInt(s.substring(1));
            for (int i = 0; i < steps; ++i) {
                point = PointsUtil.newPointNoGrid(point, direction);
//                System.out.println(point);
                if(visitedPoints.contains(point)) {
                    System.out.println(Math.abs(point.getX()) + Math.abs(point.getY()));
                    break outer;
                }
                visitedPoints.add(point);
            }
        }


    }

}
