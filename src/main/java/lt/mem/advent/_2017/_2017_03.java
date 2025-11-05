package lt.mem.advent._2017;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class _2017_03 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/3.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        int target = Integer.parseInt(lineInput.get(0));
        int circle = 1;
        int start = 2;
        while(start < target) {
//            System.out.println(start);
            start += 8 * circle;
            circle++;
        }

        circle--;
        start--;
        Point2D point;
        if(target >= (start - (circle) * 2)) {
            point = new Point2D(- circle + (target - start + (circle) * 2), circle);
        } else if (target >= (start - (circle) * 4)) {
            point = new Point2D(- circle, - circle + (target - start + (circle) * 4));
        } else if (target >= (start - (circle) * 6)) {
            point = new Point2D( circle - (target - start + (circle) * 6), - circle);
        } else {
            point = new Point2D(circle,  circle - (target - start + (circle) * 8));
        }
        System.out.println(point);

        System.out.println(Math.abs(point.getX()) + Math.abs(point.getY()));

//        System.out.println(circle);
//        System.out.println(start);

    }

    private static final Map<Point2D, Integer> values = new HashMap<>();

    public static void second(List<String> lineInput) {
        int target = Integer.parseInt(lineInput.get(0));
        Point2D point = new Point2D(0, 0);
        int step = 0;
        Direction direction = Direction.RIGHT;
        values.put(point, 1);
        while(true) {
            int steps = (step / 2) + 1;
            for(int i = 0; i < steps; ++i) {
                Point2D newPoint = PointsUtil.newPointNoGrid(point, direction);
                List<Point2D> neighbours = PointsUtil.newDiagonalPointsNoGrid(newPoint);
                int sum = 0;
                for (Point2D neighbour : neighbours) {
                    if(values.containsKey(neighbour)) {
                        sum += values.get(neighbour);
                    }
                }
                if(sum > target) {
                    System.out.println(sum);
                    return;
                }
                values.put(newPoint, sum);
                point = newPoint;
            }
            direction = PointsUtil.applyTurn(direction, Turn.LEFT);
            step++;
        }



    }

}
