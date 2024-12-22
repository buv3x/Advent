package lt.mem.advent._2024;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2024_20 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/20.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private final static Set<Point2D> walls = new HashSet<>();
    private static Point2D start;
    private static Point2D end;

    private static int MIN_CHEAT = 100;
    private static int MAX_CHEAT_DISTANCE = 20;


    public static void first(List<String> input) {
        Map<Point2D, Integer> distanceMap = fillData(input);

        int total = 0;
        for (Point2D point : distanceMap.keySet()) {
            Integer baseValue = distanceMap.get(point);
            for (Direction direction : Direction.values()) {
                Point2D skippedPoint = PointsUtil.newPointNoGrid(PointsUtil.newPointNoGrid(point, direction), direction);
                if(distanceMap.containsKey(skippedPoint) && distanceMap.get(skippedPoint) - baseValue >= MIN_CHEAT + 2) {
                    total++;
                }
            }
        }

        System.out.println(total);

    }

    public static void second(List<String> input) {
        Map<Point2D, Integer> distanceMap = fillData(input);
        int total = 0;
        for (Point2D point : distanceMap.keySet()) {
            for (Point2D cheatTarget : distanceMap.keySet()) {
                if(cheatTarget.equals(point)) {
                    continue;
                }
                int cheatDistance = PointsUtil.squareDistance(point, cheatTarget);
                if(cheatDistance > MAX_CHEAT_DISTANCE) {
                    continue;
                }

                if(distanceMap.get(cheatTarget) - distanceMap.get(point) >= cheatDistance + MIN_CHEAT) {
                    total++;
                }
            }
        }

        System.out.println(total);

    }

    private static Map<Point2D, Integer> fillData(List<String> input) {
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                Point2D point = new Point2D(j, i);
                char c = input.get(i).charAt(j);
                if(c == '#') {
                    walls.add(point);
                } else if (c == 'S') {
                    start = point;
                } else if (c == 'E') {
                    end = point;
                }
            }
        }

        Map<Point2D, Integer> distanceMap = new HashMap<>();
        distanceMap.put(start, 0);

        Point2D toProcess = start;
        while(!toProcess.equals(end)) {
            Point2D nextPoint = null;
            List<Point2D> newPoints = PointsUtil.newPointsNoGrid(toProcess);
            for (Point2D newPoint : newPoints) {
                if(!walls.contains(newPoint) && !distanceMap.containsKey(newPoint)) {
                    nextPoint = newPoint;
                    break;
                }
            }

            distanceMap.put(nextPoint, distanceMap.get(toProcess) + 1);
            toProcess = nextPoint;
        }
        return distanceMap;
    }




}
