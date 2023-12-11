package lt.mem.advent._2023;

import lt.mem.advent.util.ReaderUtil;
import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;

public class _2023_10 {

    private static final Map<Character, Function<Point2D, List<Point2D>>> map = Map.ofEntries(
            Map.entry('-', p -> new ArrayList<>(Arrays.asList(new Point2D(p.getX() - 1, p.getY()), new Point2D(p.getX() + 1, p.getY())))),
            Map.entry('|', p -> new ArrayList<>(Arrays.asList(new Point2D(p.getX(), p.getY() - 1), new Point2D(p.getX(), p.getY() + 1)))),
            Map.entry('L', p -> new ArrayList<>(Arrays.asList(new Point2D(p.getX(), p.getY() - 1), new Point2D(p.getX() + 1, p.getY())))),
            Map.entry('J', p -> new ArrayList<>(Arrays.asList(new Point2D(p.getX(), p.getY() - 1), new Point2D(p.getX() - 1, p.getY())))),
            Map.entry('F', p -> new ArrayList<>(Arrays.asList(new Point2D(p.getX(), p.getY() + 1), new Point2D(p.getX() + 1, p.getY())))),
            Map.entry('7', p -> new ArrayList<>(Arrays.asList(new Point2D(p.getX(), p.getY() + 1), new Point2D(p.getX() -1, p.getY()))))
    );

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2023/10.txt");
//        first(input);
        second(input);
    }

    public static void first(List<String> input) {
        Point2D start = findStart(input);

        Set<Point2D> visitedPoints = new HashSet<>();
        Point2D first = null;
        Point2D second = null;
        if(start.getX() > 0) {
            char c = input.get(start.getY()).charAt(start.getX() - 1);
            if(c == '-' || c == 'L' || c == 'F') {
                Point2D point = new Point2D(start.getX() - 1, start.getY());
                if(first == null) {
                    first = point;
                } else {
                    second = point;
                }
            }
        }
        if(start.getX() < input.get(0).length() - 1) {
            char c = input.get(start.getY()).charAt(start.getX() + 1);
            if(c == '-' || c == 'J' || c == '7') {
                Point2D point = new Point2D(start.getX() + 1, start.getY());
                if(first == null) {
                    first = point;
                } else {
                    second = point;
                }
            }
        }
        if(start.getY() > 0) {
            char c = input.get(start.getY() - 1).charAt(start.getX());
            if(c == '|' || c == 'F' || c == '7') {
                Point2D point = new Point2D(start.getX(), start.getY() - 1);
                if(first == null) {
                    first = point;
                } else {
                    second = point;
                }
            }
        }
        if(start.getY() < input.size() - 1) {
            char c = input.get(start.getY() + 1).charAt(start.getX());
            if(c == '|' || c == 'J' || c == 'L') {
                Point2D point = new Point2D(start.getX(), start.getY() + 1);
                if(first == null) {
                    first = point;
                } else {
                    second = point;
                }
            }
        }
        System.out.println(start);
        System.out.println(first);
        System.out.println(second);
        visitedPoints.add(start);
        visitedPoints.add(first);
        visitedPoints.add(second);

        int index = 1;
        while (!first.equals(second)) {
            char cF = getChar(input, first);
            List<Point2D> firstTransitions = map.get(cF).apply(first);
            if(visitedPoints.contains(firstTransitions.get(0))) {
                first = firstTransitions.get(1);
            } else {
                first = firstTransitions.get(0);
            }

            char cS = getChar(input, second);
            List<Point2D> secondTransitions = map.get(cS).apply(second);
            if(visitedPoints.contains(secondTransitions.get(0))) {
                second = secondTransitions.get(1);
            } else {
                second = secondTransitions.get(0);
            }
            System.out.println(first);
            System.out.println(second);
            visitedPoints.add(first);
            visitedPoints.add(second);
            index++;
        }
        System.out.println(index);
    }

    public static void second(List<String> input) {
        Point2D start = findStart(input);
        Set<Point2D> visitedPoints = new LinkedHashSet<>();
        visitedPoints.add(start);
        Point2D currentPoint = null;
        if(start.getX() > 0) {
            char c = input.get(start.getY()).charAt(start.getX() - 1);
            if(c == '-' || c == 'L' || c == 'F') {
                currentPoint = new Point2D(start.getX() - 1, start.getY());
            }
        }
        if(currentPoint == null && start.getX() < input.get(0).length() - 1) {
            char c = input.get(start.getY()).charAt(start.getX() + 1);
            if(c == '-' || c == 'J' || c == '7') {
                currentPoint = new Point2D(start.getX() + 1, start.getY());
            }
        }
        if(currentPoint == null && start.getY() > 0) {
            char c = input.get(start.getY() - 1).charAt(start.getX());
            if(c == '|' || c == 'F' || c == '7') {
                currentPoint = new Point2D(start.getX(), start.getY() - 1);
            }
        }
        if(start.getY() < input.size() - 1) {
            char c = input.get(start.getY() + 1).charAt(start.getX());
            if(c == '|' || c == 'J' || c == 'L') {
                currentPoint = new Point2D(start.getX(), start.getY() + 1);
            }
        }
        while (true) {
            visitedPoints.add(currentPoint);
//            System.out.println(currentPoint);
            List<Point2D> firstTransitions = map.get(getChar(input, currentPoint)).apply(currentPoint);
            if(visitedPoints.contains(firstTransitions.get(0))) {
                if(visitedPoints.contains(firstTransitions.get(1))) {
                    break;
                }
                currentPoint = firstTransitions.get(1);
            } else {
                currentPoint = firstTransitions.get(0);
            }
        }

        List<Point2D> visitedPointsList = new ArrayList<>(visitedPoints);
        Direction initialDirection = getInitialDirection(visitedPointsList);
        boolean lookLeft = lookLeft(input, visitedPointsList, initialDirection);

        Set<Point2D> innerPoints = new HashSet<>();
        Direction finalDirection = calculateInnerPoints(input, visitedPoints, visitedPointsList, initialDirection, lookLeft, innerPoints);
        calculateAroundStart(start, visitedPoints, initialDirection, lookLeft, innerPoints, finalDirection);

        Set<Point2D> newInnerPoints = new HashSet<>(innerPoints);
        while(CollectionUtils.isNotEmpty(newInnerPoints)) {
            Set<Point2D> toProcess = new HashSet<>();
            for (Point2D newInnerPoint : newInnerPoints) {
                Point2D p1 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() - 1);
                if(!visitedPoints.contains(p1) && !innerPoints.contains(p1)) {
                    toProcess.add(p1);
                    innerPoints.add(p1);
                }
                Point2D p2 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() + 1);
                if(!visitedPoints.contains(p2) && !innerPoints.contains(p2)) {
                    toProcess.add(p2);
                    innerPoints.add(p2);
                }
                Point2D p3 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() + 1);
                if(!visitedPoints.contains(p3) && !innerPoints.contains(p3)) {
                    toProcess.add(p3);
                    innerPoints.add(p3);
                }
                Point2D p4 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() + 1);
                if(!visitedPoints.contains(p4) && !innerPoints.contains(p4)) {
                    toProcess.add(p4);
                    innerPoints.add(p4);
                }
            }
            newInnerPoints = new HashSet<>(toProcess);
        }

        for(int i = 0; i < input.size(); ++i) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); ++j) {
                Point2D point = new Point2D(j, i);
                if(point.equals(start)) {
                    System.out.print('S');
                } else if(visitedPoints.contains(point)) {
                    System.out.print('#');
                } else if(innerPoints.contains(point)) {
                    System.out.print('o');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }
        System.out.println(innerPoints.size());
    }

    private static void calculateAroundStart(Point2D start, Set<Point2D> visitedPoints, Direction initialDirection, boolean lookLeft, Set<Point2D> innerPoints, Direction finalDirection) {
        if(finalDirection.equals(Direction.UP)) {
            if(initialDirection.equals(Direction.RIGHT)) {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() - 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() - 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else if(initialDirection.equals(Direction.LEFT)) {
                if(!lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() + 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() - 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() - 1, start.getY());
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                } else {
                    Point2D innerPoint = new Point2D(start.getX() + 1, start.getY());
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            }
        } else if(finalDirection.equals(Direction.DOWN)) {
            if(initialDirection.equals(Direction.LEFT)) {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() - 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else if(initialDirection.equals(Direction.RIGHT)) {
                if(!lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() + 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() + 1, start.getY());
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                } else {
                    Point2D innerPoint = new Point2D(start.getX() - 1, start.getY());
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            }
        } else if(finalDirection.equals(Direction.RIGHT)) {
            if(initialDirection.equals(Direction.DOWN)) {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() + 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() - 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else if(initialDirection.equals(Direction.UP)) {
                if(!lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() + 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX(), start.getY() - 1);
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                } else {
                    Point2D innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            }
        } else if(finalDirection.equals(Direction.LEFT)) {
            if(initialDirection.equals(Direction.UP)) {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() - 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else if(initialDirection.equals(Direction.DOWN)) {
                if(!lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX() - 1, start.getY());
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                    innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if(!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            } else {
                if(lookLeft) {
                    Point2D innerPoint = new Point2D(start.getX(), start.getY() + 1);
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                } else {
                    Point2D innerPoint = new Point2D(start.getX(), start.getY() - 1);
                    if (!visitedPoints.contains(innerPoint)) {
                        innerPoints.add(innerPoint);
                    }
                }
            }
        }
    }

    private static Direction calculateInnerPoints(List<String> input, Set<Point2D> visitedPoints, List<Point2D> visitedPointsList, Direction initialDirection, boolean lookLeft, Set<Point2D> innerPoints) {
        Direction direction = initialDirection;
        for (int i = 1; i < visitedPointsList.size(); ++i) {
            Point2D point = visitedPointsList.get(i);
            char c = getChar(input, point);
            switch (direction) {
                case UP: {
                    if(c == '|') {
                        Point2D innerPoint;
                        if(lookLeft) {
                            innerPoint = new Point2D(point.getX() - 1, point.getY());

                        } else {
                            innerPoint = new Point2D(point.getX() + 1, point.getY());
                        }
                        if(!visitedPoints.contains(innerPoint)) {
                            innerPoints.add(innerPoint);
                        }
                    }
                    if (c == 'F') {
                        if(lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() - 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() - 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.RIGHT;
                    }
                    if (c == '7') {
                        if(!lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() + 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() - 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.LEFT;
                    }
                    break;
                }
                case DOWN: {
                    if(c == '|') {
                        Point2D innerPoint;
                        if(lookLeft) {
                            innerPoint = new Point2D(point.getX() + 1, point.getY());

                        } else {
                            innerPoint = new Point2D(point.getX() - 1, point.getY());
                        }
                        if(!visitedPoints.contains(innerPoint)) {
                            innerPoints.add(innerPoint);
                        }
                    }
                    if (c == 'J') {
                        if(lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() + 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() + 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.LEFT;
                    }
                    if (c == 'L') {
                        if(!lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() - 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() + 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.RIGHT;
                    }
                    break;
                }
                case RIGHT: {
                    if(c == '-') {
                        Point2D innerPoint;
                        if(lookLeft) {
                            innerPoint = new Point2D(point.getX(), point.getY() - 1);

                        } else {
                            innerPoint = new Point2D(point.getX(), point.getY() + 1);
                        }
                        if(!visitedPoints.contains(innerPoint)) {
                            innerPoints.add(innerPoint);
                        }
                    }
                    if (c == '7') {
                        if(lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() + 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() - 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.DOWN;
                    }
                    if (c == 'J') {
                        if(!lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() + 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() + 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.UP;
                    }
                    break;
                }
                case LEFT: {
                    if(c == '-') {
                        Point2D innerPoint;
                        if(lookLeft) {
                            innerPoint = new Point2D(point.getX(), point.getY() + 1);

                        } else {
                            innerPoint = new Point2D(point.getX(), point.getY() - 1);
                        }
                        if(!visitedPoints.contains(innerPoint)) {
                            innerPoints.add(innerPoint);
                        }
                    }
                    if (c == 'L') {
                        if(lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() - 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() + 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.UP;
                    }
                    if (c == 'F') {
                        if(!lookLeft) {
                            Point2D innerPoint = new Point2D(point.getX() - 1, point.getY());
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                            innerPoint = new Point2D(point.getX(), point.getY() - 1);
                            if(!visitedPoints.contains(innerPoint)) {
                                innerPoints.add(innerPoint);
                            }
                        }
                        direction = Direction.DOWN;
                    }
                    break;
                }
            }
        }
        return direction;
    }

    private static boolean lookLeft(List<String> input, List<Point2D> visitedPointsList, Direction initialDirection) {
        Direction direction = initialDirection;
        int leftTurns = 0;
        int rightTurns = 0;
        for (int i = 1; i < visitedPointsList.size(); ++i) {
            char c = getChar(input, visitedPointsList.get(i));
            switch (direction) {
                case UP: {
                    if(c == 'F') {
                        rightTurns++;
                        direction = Direction.RIGHT;
                    }
                    if(c == '7') {
                        leftTurns++;
                        direction = Direction.LEFT;
                    }
                    break;
                }
                case DOWN: {
                    if(c == 'J') {
                        rightTurns++;
                        direction = Direction.LEFT;
                    }
                    if(c == 'L') {
                        leftTurns++;
                        direction = Direction.RIGHT;
                    }
                    break;
                }
                case RIGHT: {
                    if(c == '7') {
                        rightTurns++;
                        direction = Direction.DOWN;
                    }
                    if(c == 'J') {
                        leftTurns++;
                        direction = Direction.UP;
                    }
                    break;
                }
                case LEFT: {
                    if(c == 'L') {
                        rightTurns++;
                        direction = Direction.UP;
                    }
                    if(c == 'F') {
                        leftTurns++;
                        direction = Direction.DOWN;
                    }
                    break;
                }
            }
        }

        return leftTurns > rightTurns;
    }

    private static Direction getInitialDirection(List<Point2D> visitedPointsList) {
        Direction direction;
        Point2D point1 = visitedPointsList.get(0);
        Point2D point2 = visitedPointsList.get(1);
        if(point1.getX() == point2.getX()) {
            if(point1.getY() < point2.getY()) {
                direction = Direction.UP;
            } else {
                direction = Direction.DOWN;
            }
        } else {
            if(point1.getX() < point2.getX()) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
        }
        return direction;
    }

    private static char getChar(List<String> input, Point2D second) {
        char cS = input.get(second.getY()).charAt(second.getX());
        return cS;
    }

    private static Point2D findStart(List<String> input) {
        Point2D start = null;
        for(int i = 0; i < input.size(); ++i) {
            int index = input.get(i).indexOf('S');
            if(index != -1) {
                start = new Point2D(index, i);
                break;
            }
        }
        return start;
    }

}
