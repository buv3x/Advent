package lt.mem.advent._2024;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2024_8 {


    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/8.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        Map<Character, List<Point2D>> map = fillMap(input);

        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;

        Set<Point2D> points = new HashSet<>();
        for (List<Point2D> value : map.values()) {
            for(int i = 0; i < value.size(); ++i) {
                Point2D firstPoint = value.get(i);
                for(int j = i + 1; j < value.size(); ++j) {
                    Point2D secondPoint = value.get(j);
                    Pair<Point2D, Point2D> antipodes = PointsUtil.getAntipodes(firstPoint, secondPoint);
//                    System.out.println(firstPoint + " " + secondPoint + " -> " + antipodes);
                    if(PointsUtil.isInGrid(antipodes.getLeft(), maxX, maxY)) {
                        points.add(antipodes.getLeft());
//                        System.out.println(antipodes.getLeft());
                    }
                    if(PointsUtil.isInGrid(antipodes.getRight(), maxX, maxY)) {
                        points.add(antipodes.getRight());
//                        System.out.println(antipodes.getRight());
                    }
                }
            }
        }

        System.out.println(points.size());

    }

    private static Map<Character, List<Point2D>> fillMap(List<String> input) {
        Map<Character, List<Point2D>> map = new HashMap<>();
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                Character c = input.get(i).charAt(j);
                if(!c.equals('.')) {
                    Point2D point = new Point2D(j, i);
                    if(!map.containsKey(c)) {
                        map.put(c, new ArrayList<>());
                    }
                    map.get(c).add(point);
                }
            }
        }
        return map;
    }

    public static void second(List<String> input) {
        Map<Character, List<Point2D>> map = fillMap(input);

        int maxX = input.get(0).length() - 1;
        int maxY = input.size() - 1;

        Set<Point2D> points = new HashSet<>();

        for (List<Point2D> value : map.values()) {
            for (int i = 0; i < value.size(); ++i) {
                Point2D firstPoint = value.get(i);
                for (int j = i + 1; j < value.size(); ++j) {
                    Point2D secondPoint = value.get(j);
                    points.addAll(PointsUtil.getPointsOnLine(firstPoint, secondPoint, maxX, maxY));
                }
            }
        }

        System.out.println(points.size());

    }

}
