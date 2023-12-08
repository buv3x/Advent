package lt.mem.advent._2015;

import lt.mem.advent.ReaderUtil;
import lt.mem.advent.structure.Point2D;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class _2015_3 {

    private static Map<Character, Function<Point2D, Point2D>> map = Map.ofEntries(
            Map.entry('^', p -> new Point2D(p.getX(), p.getY() + 1)),
            Map.entry('>', p -> new Point2D(p.getX() + 1, p.getY())),
            Map.entry('v', p -> new Point2D(p.getX(), p.getY() - 1)),
            Map.entry('<', p -> new Point2D(p.getX() - 1, p.getY()))
    );


    public static void main(String[] args) {
        String input = ReaderUtil.readInput("_2015/3.txt");
//        first(input);
        second(input);
    }

    public static void first(String input) {
        Set<Point2D> visitedPoints = new HashSet<>();
        Point2D currentPoint = new Point2D(0, 0);
        visitedPoints.add(currentPoint);
        for (char c : input.toCharArray()) {
            currentPoint = map.get(c).apply(currentPoint);
            visitedPoints.add(currentPoint);
        }
        System.out.println(visitedPoints.size());
    }

    public static void second(String input) {
        Set<Point2D> visitedPoints = new HashSet<>();
        Point2D currentSPoint = new Point2D(0, 0);
        Point2D currentRPoint = new Point2D(0, 0);
        visitedPoints.add(currentSPoint);
        boolean santaTurn = true;
        for (char c : input.toCharArray()) {
            if(santaTurn) {
                currentSPoint = map.get(c).apply(currentSPoint);
                visitedPoints.add(currentSPoint);
            } else {
                currentRPoint = map.get(c).apply(currentRPoint);
                visitedPoints.add(currentRPoint);
            }
            santaTurn = !santaTurn;
        }
        System.out.println(visitedPoints.size());
    }

}
