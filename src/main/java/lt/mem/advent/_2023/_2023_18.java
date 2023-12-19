package lt.mem.advent._2023;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class _2023_18 {
    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/18.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        Set<Point2D> edgePoints = new LinkedHashSet<>();
        Map<Point2D, Turn> turnPoints = new HashMap<>();

        Point2D currentPoint = new Point2D(0, 0);
        edgePoints.add(currentPoint);
        int leftTurns = 0;
        int rightTurns = 0;
        Direction previousDirection = null;
        for (String line : input) {
            String directionString = ReaderUtil.stringBefore(line, " ");
            Direction direction;
            if(directionString.equals("U")) {
                direction = Direction.UP;
                if (Direction.RIGHT.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.LEFT);
                    leftTurns++;
                } else if (Direction.LEFT.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.RIGHT);
                    rightTurns++;
                }
            } else if(directionString.equals("R")) {
                direction = Direction.RIGHT;
                if (Direction.UP.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.RIGHT);
                    rightTurns++;
                } else if (Direction.DOWN.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.LEFT);
                    leftTurns++;
                }
            } else if(directionString.equals("L")) {
                direction = Direction.LEFT;
                if (Direction.UP.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.LEFT);
                    leftTurns++;
                } else if (Direction.DOWN.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.RIGHT);
                    rightTurns++;
                }
            } else {
                direction = Direction.DOWN;
                if (Direction.RIGHT.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.RIGHT);
                    rightTurns++;
                } else if (Direction.LEFT.equals(previousDirection)) {
                    turnPoints.put(currentPoint, Turn.LEFT);
                    leftTurns++;
                }
            }
            previousDirection = direction;
            int length = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, " "), " "));
            for(int i = 0; i < length; ++i) {
                switch (direction) {
                    case UP: {
                        currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() - 1);
                        break;
                    }
                    case DOWN: {
                        currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() + 1);
                        break;
                    }
                    case LEFT: {
                        currentPoint = new Point2D(currentPoint.getX() - 1, currentPoint.getY());
                        break;
                    }
                    case RIGHT: {
                        currentPoint = new Point2D(currentPoint.getX() + 1, currentPoint.getY());
                        break;
                    }
                }
                edgePoints.add(currentPoint);
            }
        }

        boolean lookLeft = leftTurns > rightTurns;
        if(lookLeft) {
            if(leftTurns - rightTurns == 3) {
                turnPoints.put(new Point2D(0, 0), Turn.LEFT);
            }
            if(leftTurns - rightTurns == 5) {
                turnPoints.put(new Point2D(0, 0), Turn.RIGHT);
            }
        } else {
            if(rightTurns - leftTurns == 3) {
                turnPoints.put(new Point2D(0, 0), Turn.RIGHT);
            }
            if(rightTurns - leftTurns == 5) {
                turnPoints.put(new Point2D(0, 0), Turn.LEFT);
            }
        }
        System.out.println(lookLeft);

        Set<Point2D> innerPoints = calculateInnderEdge(edgePoints, turnPoints, previousDirection, lookLeft);

        Set<Point2D> newInnerPoints = new HashSet<>(innerPoints);
        while(CollectionUtils.isNotEmpty(newInnerPoints)) {
            Set<Point2D> toProcess = new HashSet<>();
            for (Point2D newInnerPoint : newInnerPoints) {
                Point2D p1 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() - 1);
                if(!edgePoints.contains(p1) && !innerPoints.contains(p1)) {
                    toProcess.add(p1);
                    innerPoints.add(p1);
                }
                Point2D p2 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() + 1);
                if(!edgePoints.contains(p2) && !innerPoints.contains(p2)) {
                    toProcess.add(p2);
                    innerPoints.add(p2);
                }
                Point2D p3 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() + 1);
                if(!edgePoints.contains(p3) && !innerPoints.contains(p3)) {
                    toProcess.add(p3);
                    innerPoints.add(p3);
                }
                Point2D p4 = new Point2D(newInnerPoint.getX(), newInnerPoint.getY() + 1);
                if(!edgePoints.contains(p4) && !innerPoints.contains(p4)) {
                    toProcess.add(p4);
                    innerPoints.add(p4);
                }
            }
            newInnerPoints = new HashSet<>(toProcess);
        }

        int minX = edgePoints.stream().map(Point2D::getX).min(Comparator.comparing(Function.identity())).get();
        int maxX = edgePoints.stream().map(Point2D::getX).max(Comparator.comparing(Function.identity())).get();
        int minY = edgePoints.stream().map(Point2D::getY).min(Comparator.comparing(Function.identity())).get();
        int maxY = edgePoints.stream().map(Point2D::getY).max(Comparator.comparing(Function.identity())).get();


        for(int j = minY - 1; j <= maxY + 1; ++j) {
            for(int i = minX - 1; i <= maxX + 1; ++i) {
                Point2D point = new Point2D(i, j);
                if(edgePoints.contains(point)) {
                    System.out.print(turnPoints.containsKey(point) ? 'o' : '#');
                } else if(innerPoints.contains(point)) {
                    System.out.print('@');
                } else {
                    System.out.print('.');
                }

            }
            System.out.println();
        }

        System.out.println(innerPoints.size() + edgePoints.size());
    }

    private static Set<Point2D> calculateInnderEdge(Set<Point2D> edgePoints, Map<Point2D, Turn> turnPoints, Direction previousDirection, boolean lookLeft) {
        Set<Point2D> innerPoints = new HashSet<>();
        Direction direction = previousDirection;
        for (Point2D point : edgePoints) {
            List<Point2D> newPoints = new ArrayList<>();
            if(turnPoints.containsKey(point)) {
                Turn turn = turnPoints.get(point);
                switch (direction) {
                    case UP: {
                        if(lookLeft && turn.equals(Turn.RIGHT)) {
                            newPoints.add(new Point2D(point.getX() - 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() - 1));
                        }
                        if(!lookLeft && turn.equals(Turn.LEFT)) {
                            newPoints.add(new Point2D(point.getX() + 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() - 1));
                        }
                        break;
                    }
                    case DOWN: {
                        if(lookLeft && turn.equals(Turn.RIGHT)) {
                            newPoints.add(new Point2D(point.getX() + 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() + 1));
                        }
                        if(!lookLeft && turn.equals(Turn.LEFT)) {
                            newPoints.add(new Point2D(point.getX() - 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() + 1));
                        }
                        break;
                    }
                    case RIGHT: {
                        if(lookLeft && turn.equals(Turn.RIGHT)) {
                            newPoints.add(new Point2D(point.getX() + 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() - 1));
                        }
                        if(!lookLeft && turn.equals(Turn.LEFT)) {
                            newPoints.add(new Point2D(point.getX() + 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() + 1));
                        }
                        break;
                    }
                    case LEFT: {
                        if(lookLeft && turn.equals(Turn.RIGHT)) {
                            newPoints.add(new Point2D(point.getX() - 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() + 1));
                        }
                        if(!lookLeft && turn.equals(Turn.LEFT)) {
                            newPoints.add(new Point2D(point.getX() - 1, point.getY()));
                            newPoints.add(new Point2D(point.getX(), point.getY() - 1));
                        }
                        break;
                    }
                }
                direction = PointsUtil.applyTurn(direction, turn);
            } else {
                switch (direction) {
                    case UP: {
                        newPoints.add(new Point2D(point.getX() + (lookLeft ?  -1 : 1), point.getY()));
                        break;
                    }
                    case DOWN: {
                        newPoints.add(new Point2D(point.getX() + (lookLeft ?  1 : -1), point.getY()));
                        break;
                    }
                    case RIGHT: {
                        newPoints.add(new Point2D(point.getX(), point.getY() + (lookLeft ?  -1 : 1)));
                        break;
                    }
                    case LEFT: {
                        newPoints.add(new Point2D(point.getX(), point.getY() + (lookLeft ?  1 : -1)));
                        break;
                    }
                }
            }

            for (Point2D newPoint : newPoints) {
                if(!edgePoints.contains(newPoint)) {
                    innerPoints.add(newPoint);
                }
            }
        }
        return innerPoints;
    }

    public static void second(List<String> input) {
        List<Line> verticalLines = new ArrayList<>();
        List<Line> horizontalLines = new ArrayList<>();

        Point2D currentPoint = new Point2D(0, 0);
        for (String line : input) {
            String hexString = ReaderUtil.stringBefore(ReaderUtil.stringAfter(line, "#"), ")");
            char direction = hexString.charAt(5);
            int length = Integer.parseInt(hexString.substring(0, 5), 16);
            if(direction == '3') {
                verticalLines.add(new Line(currentPoint.getY() - length, currentPoint.getY(), currentPoint.getX()));
                currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() - length);
            } else if(direction == '0') {
                horizontalLines.add(new Line(currentPoint.getX(), currentPoint.getX() + length, currentPoint.getY()));
                currentPoint = new Point2D(currentPoint.getX() + length, currentPoint.getY());
            } else if(direction == '2') {
                horizontalLines.add(new Line(currentPoint.getX() - length, currentPoint.getX(), currentPoint.getY()));
                currentPoint = new Point2D(currentPoint.getX() - length, currentPoint.getY());
            } else {
                verticalLines.add(new Line(currentPoint.getY(), currentPoint.getY() + length, currentPoint.getX()));
                currentPoint = new Point2D(currentPoint.getX(), currentPoint.getY() + length);
            }
        }

        System.out.println(horizontalLines);
        System.out.println(verticalLines);

        long total = 0;
        List<Integer> verticals = verticalLines.stream().map(Line::getCoordinate).distinct().sorted().collect(Collectors.toList());
        for(int i = 0; i < verticals.size() - 1; ++i) {
            if(verticals.get(i + 1) - verticals.get(i) > 1) {
                int coordinate = verticals.get(i) + 1;
                List<Line> horizontalList = horizontalLines.stream().filter(l -> l.from <= coordinate && l.to >= coordinate)
                        .sorted(Comparator.comparing(Line::getCoordinate)).collect(Collectors.toList());
//                System.out.println(coordinate + " " + horizontalList);
                for(int j = 0; j < horizontalList.size() / 2; ++j) {
                    total += (long) (horizontalList.get(2 * j + 1).coordinate - horizontalList.get(2 * j).coordinate + 1) *
                            (verticals.get(i + 1) - verticals.get(i) - 1);
                }
            }
        }

        for (Integer vertical : verticals) {
            List<Line> horizontalList = horizontalLines.stream().filter(l -> l.from <= vertical && l.to >= vertical)
                    .sorted(Comparator.comparing(Line::getCoordinate)).collect(Collectors.toList());
            boolean open = false;
            int openStart = 0;
            boolean openRight = false;
            boolean openLeft = false;
            int openSide = 0;
            int totalForVertical = 0;

            if(vertical.equals(10093655)) {
                int i = 0;
                ++i;
            }

            System.out.println(vertical + " " + horizontalList);
            List<String> differences = new ArrayList<>();
            for (Line horizontalLine : horizontalList) {
                if (horizontalLine.from < vertical && horizontalLine.to > vertical) {
                    if (!open) {
                        open = true;
                        openStart = horizontalLine.coordinate;
                    } else {
                        differences.add("(" + horizontalLine.coordinate + " - " + openStart + ")");
                        totalForVertical += horizontalLine.coordinate - openStart + 1;
                        open = false;
                    }
                } else {
                    if (horizontalLine.from == vertical) {
                        if (!openLeft && !openRight) {
                            openRight = true;
                            openSide = horizontalLine.coordinate;
//                            if (!open) {
//                                open = true;
//                                openStart = horizontalLine.coordinate;
//                            }
                        } else {
                            if (openRight) {
                                if (!open) {
                                    differences.add("(" + horizontalLine.coordinate + " - " + openSide + ")");
                                    totalForVertical += horizontalLine.coordinate - openSide + 1;
                                }
                                openRight = false;
                            } else {
                                if (!open) {
                                    open = true;
                                    openStart = openSide;
                                } else {
                                    differences.add("(" + horizontalLine.coordinate + " - " + openStart + ")");
                                    totalForVertical += horizontalLine.coordinate - openStart + 1;
                                    open = false;
                                }
                                openLeft = false;
                            }
                        }
                    } else {
                        if (!openLeft && !openRight) {
                            openLeft = true;
                            openSide = horizontalLine.coordinate;
//                            if (!open) {
//                                open = true;
//                                openStart = horizontalLine.coordinate;
//                            }
                        } else {
                            if (openLeft) {
                                if (!open) {
                                    differences.add("(" + horizontalLine.coordinate + " - " + openSide + ")");
                                    totalForVertical += horizontalLine.coordinate - openSide + 1;
                                }
                                openLeft = false;
                            } else {
                                if (!open) {
                                    open = true;
                                    openStart = openSide;
                                } else {
                                    differences.add("(" + horizontalLine.coordinate + " - " + openStart + ")");
                                    totalForVertical += horizontalLine.coordinate - openStart + 1;
                                    open = false;
                                }
                                openRight = false;
                            }
                        }
                    }
                }
            }
            System.out.println(vertical + " " + StringUtils.join(differences, ", "));
            System.out.println(vertical + " " + totalForVertical);
            total += totalForVertical;
        }
        System.out.println(total);

    }

    private static class Line {
        final int from;
        final int to;
        final int coordinate;

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public int getCoordinate() {
            return coordinate;
        }

        public Line(int from, int to, int coordinate) {
            this.from = from;
            this.to = to;
            this.coordinate = coordinate;
        }

        @Override
        public String toString() {
            return "(" + from + "," + to + ") - " + coordinate;
        }
    }


}
