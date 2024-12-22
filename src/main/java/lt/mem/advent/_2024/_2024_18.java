package lt.mem.advent._2024;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;

public class _2024_18 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/18.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final int MAX_GRID = 70;
    private static final int TIME = 0;

    private static final Point2D start = new Point2D(0, 0);
    private static final Point2D end = new Point2D(MAX_GRID, MAX_GRID);

    private static Set<Point2D> walls;

    public static void first(List<String> input) {
        fillGrid(input, TIME);

        Set<Point2D> usedPoints = new HashSet<>();
        Set<Point2D> toProcess = new HashSet<>();
        toProcess.add(start);

        int i = 0;
        while(CollectionUtils.isNotEmpty(toProcess) || !toProcess.contains(end)) {
            Set<Point2D> newPoints = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> neighbors = PointsUtil.newPoints(point, MAX_GRID, MAX_GRID);
                for (Point2D neighbor : neighbors) {
                    if(!walls.contains(neighbor) && !usedPoints.contains(neighbor) && !toProcess.contains(neighbor)) {
                        newPoints.add(neighbor);
                    }
                }
            }

            ++i;
            usedPoints.addAll(toProcess);
            toProcess = newPoints;
        }

        System.out.println(i);

    }

    private static void fillGrid(List<String> input, int time) {
        walls = new HashSet<>();
        for(int i = 0; i < time; i++) {
            walls.add(new Point2D(Integer.parseInt(ReaderUtil.stringBefore(input.get(i), ",")),
                    Integer.parseInt(ReaderUtil.stringAfter(input.get(i), ","))));
        }
    }


    public static void second(List<String> input) {
        int proc = TIME;
        int unproc = input.size();

        while(proc + 1 != unproc) {
            int time = proc + ((unproc - proc) / 2);
            boolean processable = process(input, time);
            if(processable) {
                proc = time;
            } else {
                unproc = time;
            }
        }

        System.out.println(unproc);
        System.out.println(input.get(proc));
    }

    private static boolean process(List<String> input, int time) {
        fillGrid(input, time);

        Set<Point2D> usedPoints = new HashSet<>();
        Set<Point2D> toProcess = new HashSet<>();
        toProcess.add(start);

        while(CollectionUtils.isNotEmpty(toProcess) && !toProcess.contains(end)) {
            Set<Point2D> newPoints = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> neighbors = PointsUtil.newPoints(point, MAX_GRID, MAX_GRID);
                for (Point2D neighbor : neighbors) {
                    if(!walls.contains(neighbor) && !usedPoints.contains(neighbor) && !toProcess.contains(neighbor)) {
                        newPoints.add(neighbor);
                    }
                }
            }

            usedPoints.addAll(toProcess);
            toProcess = newPoints;
        }
        return CollectionUtils.isNotEmpty(toProcess);
    }


}
