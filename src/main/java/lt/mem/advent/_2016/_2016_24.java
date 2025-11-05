package lt.mem.advent._2016;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2016_24 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/24.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private final static Set<Point2D> walls = new HashSet<>();
    private final static Map<Integer, Map<Integer, Integer>> pathes = new HashMap<>();
    private final static Map<Integer, Point2D> nodes = new HashMap<>();

    public static void first(List<String> lineInput) {
        parse(lineInput);
        System.out.println(nodes);
        calculatePathes();
        System.out.println(pathes);

        List<Integer> path = new ArrayList<>();
        path.add(0);
        System.out.println(calculate(path, 0, false));
    }

    private static int calculate(List<Integer> path, int total, boolean withReturn) {
        if(path.size() == nodes.size()) {
            if(withReturn) {
//                System.out.println(path + " " + (total + pathes.get(path.get(path.size() - 1)).get(0)));
                return total + pathes.get(path.get(path.size() - 1)).get(0);
            } else {
                return total;
            }
        } else {
            Integer destination = path.get(path.size() - 1);
            int minTotal = Integer.MAX_VALUE;
            for (Map.Entry<Integer, Integer> entry : pathes.get(destination).entrySet()) {
                if(!path.contains(entry.getKey())) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(entry.getKey());
                    int newTotal = calculate(newPath, total + entry.getValue(), withReturn);
                    if(newTotal < minTotal) {
                        minTotal = newTotal;
                    }
                }
            }
            return minTotal;
        }
    }

    private static void calculatePathes() {
        for (Integer index : nodes.keySet()) {
            pathes.put(index, new HashMap<>());
        }

        for (Integer index : nodes.keySet()) {
            Point2D node = nodes.get(index);
            Set<Point2D> processed = new HashSet<>();
            Set<Point2D> toProcess = new HashSet<>();
            toProcess.add(node);
            processed.add(node);

            int distance = 1;
            while(pathes.get(index).size() < nodes.size() - 1) {
                Set<Point2D> newPoints = new HashSet<>();
                for (Point2D point : toProcess) {
                    List<Point2D> neighbors = PointsUtil.newPointsNoGrid(point);
                    for (Point2D newPoint : neighbors) {
                        if(walls.contains(newPoint) || processed.contains(newPoint)) {
                            continue;
                        }
                        processed.add(newPoint);
                        newPoints.add(newPoint);
                        for (Map.Entry<Integer, Point2D> entry : nodes.entrySet()) {
                            if(entry.getValue().equals(newPoint)) {
                                pathes.get(index).put(entry.getKey(), distance);
                                pathes.get(entry.getKey()).put(index, distance);
                            }
                        }
                    }
                }
                toProcess = newPoints;
                distance++;
            }
        }
    }

    private static void parse(List<String> lineInput) {
        for(int i = 0; i < lineInput.size(); ++i) {
            for (int j = 0; j < lineInput.get(i).length(); ++j) {
                Point2D point = new Point2D(j, i);
                char c = lineInput.get(i).charAt(j);
                if(c == '#') {
                    walls.add(point);
                } else if(c >= '0' && c <= '9') {
                    nodes.put(Integer.valueOf(c + ""), point);
                }
            }
        }
    }

    public static void second(List<String> lineInput) {
        parse(lineInput);
        System.out.println(nodes);
        calculatePathes();
        System.out.println(pathes);

        List<Integer> path = new ArrayList<>();
        path.add(0);
        System.out.println(calculate(path, 0, true));
    }

}
