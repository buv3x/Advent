package lt.mem.advent._2025;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2025_7 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2025/7.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        Set<Point2D> grid = new HashSet<>();
        int startRow = 0;
        int limit = input.size();
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                char c = input.get(i).charAt(j);
                if(c == 'S') {
                    startRow = j;
                } else if(c == '^') {
                    grid.add(new Point2D(j, i));
                }
            }
        }

        int total = 0;
        Set<Integer> rays = new HashSet<>();
        rays.add(startRow);
        for(int i = 1; i < limit; ++i) {
            Set<Integer> newRays = new HashSet<>();
            for (Integer ray : rays) {
                if(grid.contains(new Point2D(ray, i))) {
                    newRays.add(ray - 1);
                    newRays.add(ray + 1);
                    total++;
                } else {
                    newRays.add(ray);
                }
            }
            rays = newRays;
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        Set<Point2D> grid = new HashSet<>();
        int startRow = 0;
        int limit = input.size();
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                char c = input.get(i).charAt(j);
                if(c == 'S') {
                    startRow = j;
                } else if(c == '^') {
                    grid.add(new Point2D(j, i));
                }
            }
        }

        long total = calculate(limit, new Point2D(startRow, 0), grid);
        System.out.println(total);
    }

    private static final Map<Point2D, Long> cache = new HashMap<>();

    private static long calculate(int limit, Point2D point, Set<Point2D> grid) {
        if(point.getY() >= limit) {
            return 1;
        }
        if(cache.containsKey(point)) {
            return cache.get(point);
        }
        long total;
        if(grid.contains(point)) {
            total = calculate(limit, new Point2D(point.getX() - 1, point.getY() + 1), grid) +
                    calculate(limit, new Point2D(point.getX() + 1, point.getY() + 1), grid);
        } else {
            total = calculate(limit, new Point2D(point.getX(), point.getY() + 1), grid);
        }
        cache.put(point, total);
        return total;
    }

}
