package lt.mem.advent._2023;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class _2023_21 {

    private static long STEPS = 26501365L;

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/21.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void second(List<String> input) {
        Point2D startPoint = null;
//        outerloop:
//        for (int i = 0; i < input.size(); ++i) {
//            for (int j = 0; j < input.get(i).length(); ++j) {
//                if(input.get(i).charAt(j) == 'S') {
//                    startPoint = new Point2D(j, i);
//                    break outerloop;
//                }
//            }
//        }
//         bruteForce(input, startPoint);

        int dimension = input.size() - 1;
        startPoint = new Point2D(dimension / 2, dimension / 2);

        Set<Point2D> visitedPoints = new HashSet<>();
        visitedPoints.add(startPoint);
        Set<Point2D> evenPoints = new HashSet<>();
        evenPoints.add(startPoint);
        Set<Point2D> oddPoints = new HashSet<>();

        Set<Point2D> toProcess = new HashSet<>();
        toProcess.add(startPoint);
        int step = 1;
        while(CollectionUtils.isNotEmpty(toProcess)) {
            Set<Point2D> newPoints = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> neighbours = PointsUtil.newPoints(point, 0, 0, dimension, dimension);
                for (Point2D neighbour : neighbours) {
                    if(isFree(neighbour, input) && !visitedPoints.contains(neighbour)) {
                        newPoints.add(neighbour);
                    }
                }
            }
            visitedPoints.addAll(newPoints);
            if(step % 2 == 0) {
                evenPoints.addAll(newPoints);
            } else {
                oddPoints.addAll(newPoints);
            }
            step++;
            toProcess = newPoints;
        }
        int evenSize = evenPoints.size();
        int oddSize = oddPoints.size();
        System.out.println("evenSize: " + evenSize);
        System.out.println("oddSize: " + oddSize);

        long fullShift = dimension + 2;
        long halfShift = fullShift / 2;
        long stepSize = dimension + 1;

        // Initial square
        long total = 0;
        total += oddSize;

        // Straight
        long straightRemainder = STEPS - halfShift;
        long straightCalculatedSteps = (straightRemainder / stepSize) - 1;
        System.out.println("straightCalculatedSteps: " + straightCalculatedSteps);
        long totalStraight = (long) oddSize * (straightCalculatedSteps / 2);
        totalStraight += (long) evenSize * (straightCalculatedSteps - straightCalculatedSteps / 2);
//        System.out.println(totalStraight);

//        System.out.println(remainingSteps);

        total += totalStraight * 4;

        long remainingSteps = straightRemainder - straightCalculatedSteps * (dimension + 1);
        Point2D straightStartPoint = new Point2D(0, dimension / 2);
        long rightStraight = calculatePoints(input, straightStartPoint,remainingSteps)
                + calculatePoints(input, straightStartPoint, remainingSteps - dimension - 1);

        straightStartPoint = new Point2D(dimension, dimension / 2);
        long leftStraight = calculatePoints(input, straightStartPoint,remainingSteps)
                + calculatePoints(input, straightStartPoint, remainingSteps - dimension - 1);

        straightStartPoint = new Point2D(dimension / 2, 0);
        long topStraight = calculatePoints(input, straightStartPoint,remainingSteps)
                + calculatePoints(input, straightStartPoint, remainingSteps - dimension - 1);

        straightStartPoint = new Point2D(dimension / 2, dimension);
        long bottomStraight = calculatePoints(input, straightStartPoint,remainingSteps)
                + calculatePoints(input, straightStartPoint, remainingSteps - dimension - 1);

        total += rightStraight + leftStraight + topStraight + bottomStraight;

        long diagRemainder = STEPS - fullShift;
        long diagCalculatedSteps = (diagRemainder / stepSize) - 1;
        long evenStepsCount = diagCalculatedSteps / 2;
        long oddStepsCount = diagCalculatedSteps - evenStepsCount;

        long evenCount = evenStepsCount * (4 + (evenStepsCount - 1) * 2L) / 2;
        long oddCount = oddStepsCount * (2 + (oddStepsCount - 1) * 2L) / 2;
        System.out.println(evenCount);
        System.out.println(oddCount);

        total += 4 * (((long) evenCount * evenSize) + ((long) oddCount * oddSize));

        System.out.println("diagCalculatedSteps: " + diagCalculatedSteps);

        long remainingDiagSteps = diagRemainder - diagCalculatedSteps * (dimension + 1);
        Point2D diagStartPoint = new Point2D(0, 0);
        long diag1 = calculatePoints(input, diagStartPoint,remainingDiagSteps) * (diagCalculatedSteps + 1)
                + calculatePoints(input, diagStartPoint, remainingDiagSteps - dimension - 1) * (diagCalculatedSteps + 2);

        diagStartPoint = new Point2D(dimension, 0);
        long diag2 = calculatePoints(input, diagStartPoint,remainingDiagSteps) * (diagCalculatedSteps + 1)
                + calculatePoints(input, diagStartPoint, remainingDiagSteps - dimension - 1) * (diagCalculatedSteps + 2);

        diagStartPoint = new Point2D(0, dimension);
        long diag3 = calculatePoints(input, diagStartPoint,remainingDiagSteps) * (diagCalculatedSteps + 1)
                + calculatePoints(input, diagStartPoint, remainingDiagSteps - dimension - 1) * (diagCalculatedSteps + 2);

        diagStartPoint = new Point2D(dimension, dimension);
        long diag4 = calculatePoints(input, diagStartPoint,remainingDiagSteps) * (diagCalculatedSteps + 1)
                + calculatePoints(input, diagStartPoint, remainingDiagSteps - dimension - 1) * (diagCalculatedSteps + 2);

        total += diag1 + diag2 + diag3 + diag4;



        System.out.println(total);
    }

    private static long calculatePoints(List<String> input, Point2D startPoint, long steps) {
        Set<Point2D> visitedPoints = new HashSet<>();
        visitedPoints.add(startPoint);
        Set<Point2D> evenPoints = new HashSet<>();
        if(steps % 2 == 0) {
            evenPoints.add(startPoint);
        }
//        evenPoints.add(startPoint);

        Set<Point2D> toProcess = new HashSet<>();
        toProcess.add(startPoint);
        for(int step = 1; step <= steps; ++step) {
            Set<Point2D> newPoints = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> neighbours = PointsUtil.newPoints(point, input.size() - 1, input.size() - 1);
                for (Point2D neighbour : neighbours) {
                    if(isFree(neighbour, input) && !visitedPoints.contains(neighbour)) {
                        newPoints.add(neighbour);
                    }
                }
            }
            visitedPoints.addAll(newPoints);
            if(step % 2 == steps % 2) {
                evenPoints.addAll(newPoints);
            }
            toProcess = newPoints;
        }
        return evenPoints.size();
    }

    private static void bruteForce(List<String> input, Point2D startPoint) {
        Set<Point2D> visitedPoints = new HashSet<>();
        visitedPoints.add(startPoint);
        Set<Point2D> evenPoints = new HashSet<>();
//        evenPoints.add(startPoint);

        Set<Point2D> toProcess = new HashSet<>();
        toProcess.add(startPoint);
        for(int step = 1; step <= STEPS; ++step) {
            Set<Point2D> newPoints = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> neighbours = PointsUtil.newPoints(point, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
                for (Point2D neighbour : neighbours) {
                    if(isFree(neighbour, input) && !visitedPoints.contains(neighbour)) {
                        newPoints.add(neighbour);
                    }
                }
            }
            visitedPoints.addAll(newPoints);
            if(step % 2 == STEPS % 2) {
                evenPoints.addAll(newPoints);
            }
            toProcess = newPoints;
        }
        System.out.println(evenPoints.size());
    }

    private static boolean isFree(Point2D point, List<String> input) {
        int width = input.get(0).length();
        int height = input.size();
        int x = (point.getX() % width + width) % width;
        int y = (point.getY() % height + height) % height;

        return input.get(y).charAt(x) != '#';
    }

// -- First -------------------------------------------------------------------------------------------------

    public static void first(List<String> input) {
        Point2D startPoint = null;
        outerloop:
        for (int i = 0; i < input.size(); ++i) {
            for (int j = 0; j < input.get(i).length(); ++j) {
                if(input.get(i).charAt(j) == 'S') {
                    startPoint = new Point2D(j, i);
                    break outerloop;
                }
            }
        }

        int maxWidth = input.get(0).length() - 1;
        int maxHeight = input.size() - 1;
        System.out.println(startPoint);

        Set<Point2D> visitedPoints = new HashSet<>();
        visitedPoints.add(startPoint);
        Set<Point2D> evenPoints = new HashSet<>();
        evenPoints.add(startPoint);

        Set<Point2D> toProcess = new HashSet<>();
        toProcess.add(startPoint);
        for(int step = 1; step <= STEPS; ++step) {
            Set<Point2D> newPoints = new HashSet<>();
            for (Point2D point : toProcess) {
                List<Point2D> neighbours = PointsUtil.newPoints(point, maxWidth, maxHeight);
                for (Point2D neighbour : neighbours) {
                    if(isFree(neighbour, input) && !visitedPoints.contains(neighbour)) {
                        newPoints.add(neighbour);
                    }
                }
            }
            visitedPoints.addAll(newPoints);
            if(step % 2 == STEPS % 2) {
                evenPoints.addAll(newPoints);
            }
            toProcess = newPoints;
        }
        System.out.println(evenPoints.size());
    }

}
