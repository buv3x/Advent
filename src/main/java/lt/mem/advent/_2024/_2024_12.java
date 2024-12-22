package lt.mem.advent._2024;

import lt.mem.advent.structure.DirectedPoint2D;
import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class _2024_12 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/12.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<Point2D, Character> grid = new HashMap<>();
    
    public static void first(List<String> input) {
        fillGrid(input);
        List<Set<Point2D>> regions = fillRegions();

        int total = 0;
        for (Set<Point2D> region : regions) {
            int totalFences = 0;
            for (Point2D point : region) {
                List<Point2D> potentialFences = PointsUtil.newPointsNoGrid(point);
                for (Point2D potential : potentialFences) {
                    if(!grid.containsKey(potential) || !grid.get(potential).equals(grid.get(point))) {
                        totalFences++;
                    }
                }
            }
            total += totalFences * region.size();
        }
        System.out.println(total);
    }

    public static void second(List<String> input) {
        fillGrid(input);
        List<Set<Point2D>> regions = fillRegions();

        int total = 0;
        for (Set<Point2D> region : regions) {
            Character character = grid.get(region.iterator().next());
            List<Set<DirectedPoint2D>> lines = new ArrayList<>();
            Set<DirectedPoint2D> usedPoints = new HashSet<>();
            for (Point2D point : region) {
                for (Direction direction : Direction.values()) {
                    if(usedPoints.contains(new DirectedPoint2D(point, direction))) {
                        continue;
                    }
                    Point2D neighbourPoint = PointsUtil.newPointNoGrid(point, direction);
                    if(!grid.containsKey(neighbourPoint) || !grid.get(neighbourPoint).equals(character)) {
                        Set<Point2D> line = new HashSet<>();
                        line.add(point);
                        Set<Point2D> newPoints = new HashSet<>();
                        newPoints.add(point);
                        while(CollectionUtils.isNotEmpty(newPoints)) {
                            Set<Point2D> pointsToProcess = new HashSet<>();
                            for (Point2D newPoint : newPoints) {
                                Direction left = PointsUtil.applyTurn(direction, Turn.LEFT);
                                Point2D leftPoint = PointsUtil.newPointNoGrid(newPoint, left);
                                if(!line.contains(leftPoint)) {
                                    if (grid.containsKey(leftPoint) && grid.get(leftPoint).equals(character)) {
                                        Point2D leftNeighbour = PointsUtil.newPointNoGrid(leftPoint, direction);
                                        if (!grid.containsKey(leftNeighbour) || !grid.get(leftNeighbour).equals(character)) {
                                            pointsToProcess.add(leftPoint);
                                        }
                                    }
                                }
                                Direction right = PointsUtil.applyTurn(direction, Turn.RIGHT);
                                Point2D rightPoint = PointsUtil.newPointNoGrid(newPoint, right);
                                if(!line.contains(rightPoint)) {
                                    if (grid.containsKey(rightPoint) && grid.get(rightPoint).equals(character)) {
                                        Point2D rightNeighbour = PointsUtil.newPointNoGrid(rightPoint, direction);
                                        if (!grid.containsKey(rightNeighbour) || !grid.get(rightNeighbour).equals(character)) {
                                            pointsToProcess.add(rightPoint);
                                        }
                                    }
                                }
                            }
                            line.addAll(pointsToProcess);
                            newPoints = pointsToProcess;
                        }
                        Set<DirectedPoint2D> directedLine = new HashSet<>();
                        for (Point2D linePoint : line) {
                            DirectedPoint2D directedLinePoint = new DirectedPoint2D(linePoint, direction);
                            directedLine.add(directedLinePoint);
                        }
                        usedPoints.addAll(directedLine);
                        lines.add(directedLine);
                    }
                }
            }

//            System.out.println(region);
//            System.out.println(lines);
//            System.out.println();
            total += region.size() * lines.size();
        }
        System.out.println(total);
    }


    private static List<Set<Point2D>> fillRegions() {
        Set<Point2D> processedPoints = new HashSet<>();
        List<Set<Point2D>> regions = new ArrayList<>();
        for (Point2D point : grid.keySet()) {
            if(processedPoints.contains(point)) {
                continue;
            }
            Set<Point2D> region = new HashSet<>();
            Set<Point2D> newPoints = new HashSet<>();
            newPoints.add(point);
            region.add(point);
            while(CollectionUtils.isNotEmpty(newPoints)) {
                Set<Point2D> neighbours = new HashSet<>();
                for (Point2D newPoint : newPoints) {
                    List<Point2D> potentialRegion = PointsUtil.newPointsNoGrid(newPoint);
                    for (Point2D potential : potentialRegion) {
                        if (grid.containsKey(potential) && grid.get(newPoint).equals(grid.get(potential)) && !region.contains(potential)) {
                            neighbours.add(potential);
                        }
                    }
                }
                region.addAll(neighbours);
                newPoints = neighbours;
            }
            regions.add(region);
            processedPoints.addAll(region);
        }
        return regions;
    }



    private static void fillGrid(List<String> input) {
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                Point2D point = new Point2D(j, i);
                grid.put(point, input.get(i).charAt(j));
            }
        }
    }

}
