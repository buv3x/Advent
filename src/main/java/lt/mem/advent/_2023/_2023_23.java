package lt.mem.advent._2023;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class _2023_23 {

    private static Point2D startPoint = null;
    private static Point2D endPoint = null;
    private final static List<List<Node>> nodes = new ArrayList<>();
    private final static Set<Point2D> junctionPoints = new LinkedHashSet<>();
    private final static Map<Point2D, Map<Point2D, Integer>> distancesMap = new HashMap<>();

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/23.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        parseNodes(input);

        System.out.println(startPoint);
        System.out.println(endPoint);

        int length = processPoint(startPoint, new HashSet<>(), 0);
        System.out.println(length);
    }

    public static void second(List<String> input) {
        parseNodes(input);
        fillJunctionPoints();
        System.out.println(junctionPoints);

        fillDistancesMap();
        System.out.println(distancesMap);

        int length = processPoint2(startPoint, new HashSet<>(), 0);
        System.out.println(length);

//        int length = processPoint(startPoint, new HashSet<>(), 0);
//        System.out.println(length);
    }

    private static int CURRENT_MAX = 0;

    private static int processPoint2(Point2D point, Set<Point2D> visitedPoints, int currentLength) {
        if(point.equals(endPoint)) {
            if(currentLength > CURRENT_MAX) {
                System.out.println(currentLength + "    " + visitedPoints);
                CURRENT_MAX = currentLength;
            }
            return currentLength;
        }

        Map<Point2D, Integer> distances = distancesMap.get(point);
        int max = 0;
        for (Point2D destinationPoint : distances.keySet()) {
            if(!visitedPoints.contains(destinationPoint)) {
                HashSet<Point2D> newVisitedPoints = new HashSet<>(visitedPoints);
                newVisitedPoints.add(point);
                int length = processPoint2(destinationPoint, newVisitedPoints,
                        currentLength + distances.get(destinationPoint));
                if(length > max) {
                    max = length;
                }
            }
        }
        return max;
    }

    private static void fillDistancesMap() {
        for (Point2D junctionPoint : junctionPoints) {
            List<Point2D> neighbours = PointsUtil.newPoints(junctionPoint, nodes.get(0).size() - 1, nodes.size() - 1);
            distancesMap.put(junctionPoint, new HashMap<>());
            for (Point2D neighbour : neighbours) {
                if(getNode(neighbour).open) {
                    JunctionDistance junctionDistance = findDistance(neighbour, junctionPoint, 1);
                    if(distancesMap.get(junctionPoint).containsKey(junctionDistance.point)) {
                        Integer distance = distancesMap.get(junctionPoint).get(junctionDistance.point);
                        System.out.println(junctionPoint + " - " + junctionDistance.point);
                        if(junctionDistance.distance > distance) {
                            distancesMap.get(junctionPoint).put(junctionDistance.point, junctionDistance.distance);
                        }
                    } else {
                        distancesMap.get(junctionPoint).put(junctionDistance.point, junctionDistance.distance);
                    }
                }
            }
        }
    }

    private static JunctionDistance findDistance(Point2D point, Point2D pastPoint, int length) {
        if(junctionPoints.contains(point)) {
            return new JunctionDistance(length, point);
        }
        List<Point2D> newPoints = PointsUtil.newPoints(point, nodes.get(0).size() - 1, nodes.size() - 1);
        for (Point2D newPoint : newPoints) {
            if(!newPoint.equals(pastPoint) && getNode(newPoint).open) {
                return findDistance(newPoint, point, length + 1);
            }
        }
        throw new IllegalArgumentException();
    }

    private static class JunctionDistance {
        int distance;
        Point2D point;

        public JunctionDistance(int distance, Point2D point) {
            this.distance = distance;
            this.point = point;
        }
    }

    private static void fillJunctionPoints() {
        junctionPoints.add(startPoint);
        for(int i = 0; i < nodes.size(); ++i) {
            for (int j = 0; j < nodes.get(i).size(); ++j) {
                Point2D point = new Point2D(j, i);
                Node node = getNode(point);
                if(node.open) {
                    List<Point2D> newPoints = PointsUtil.newPoints(point, nodes.get(0).size() - 1, nodes.size() - 1);
                    int directions = 0;
                    for (Point2D newPoint : newPoints) {
                        if(getNode(newPoint).open) {
                            ++directions;
                        }
                    }
                    if(directions > 2) {
                        junctionPoints.add(point);
                    }
                }
            }
        }
        junctionPoints.add(endPoint);
    }

    private static void parseNodes(List<String> input) {
        for(int i = 0; i < input.size(); ++i) {
            List<Node> nodesRow = new ArrayList<>();
            for(int j = 0; j < input.get(i).length(); ++j) {
                char c = input.get(i).charAt(j);
                if(i == 0 && c == '.') {
                    startPoint = new Point2D(j, i);
                }
                if(i == input.size() - 1 && c == '.') {
                    endPoint = new Point2D(j, i);
                }
                Node node;
                if(c == '.') {
                    node = new Node(true, null);
                } else if(c == '<') {
                    node = new Node(true, Direction.LEFT);
                } else if(c == '^') {
                    node = new Node(true, Direction.UP);
                } else if(c == '>') {
                    node = new Node(true, Direction.RIGHT);
                } else if(c == 'v') {
                    node = new Node(true, Direction.DOWN);
                } else {
                    node = new Node(false, null);
                }

                nodesRow.add(node);
            }
            nodes.add(nodesRow);
        }
    }

    private static int processPoint(Point2D point, Set<Point2D> visitedPoints, int currentLength) {
        if(point.equals(endPoint)) {
            return currentLength;
        }
        visitedPoints.add(point);
        List<Point2D> nextPoints = new ArrayList<>();
        Node node = getNode(point);
        if(node.forcedDirection != null) {
            nextPoints.add(PointsUtil.newPoint(point, node.forcedDirection, nodes.get(0).size() - 1, nodes.size() - 1));
        } else {
            nextPoints.addAll(PointsUtil.newPoints(point, nodes.get(0).size() - 1, nodes.size() - 1));
        }

        List<Point2D> filteredPoints = new ArrayList<>();
        for (Point2D nextPoint : nextPoints) {
            if(!visitedPoints.contains(nextPoint)) {
                Node nextNode = getNode(nextPoint);
                if(nextNode.open) {
                    filteredPoints.add(nextPoint);
                }
            }
        }

        if(CollectionUtils.isNotEmpty(filteredPoints)) {
            int max = 0;
            for (Point2D filteredPoint : filteredPoints) {
                Set<Point2D> newVisitedPoints = filteredPoints.size() > 1 ? new HashSet<>(visitedPoints) : visitedPoints;
                int length = processPoint(filteredPoint, newVisitedPoints, currentLength + 1);
                if(length > max) {
                    max = length;
                }
            }
            return max;
        } else {
            return 0;
        }
    }

    private static Node getNode(Point2D point) {
        return nodes.get(point.getY()).get(point.getX());
    }

    private static class Node {
        boolean open;
        Direction forcedDirection;

        public Node(boolean open, Direction forcedDirection) {
            this.open = open;
            this.forcedDirection = forcedDirection;
        }
    }

}
