package lt.mem.advent._2016;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2016_22 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/22.txt");
        long timeMillis = System.currentTimeMillis();
//        first(lineInput);
        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> lineInput) {
        Map<Point2D, Pair<Integer, Integer>> nodes = new HashMap<>();
        for(int i = 2; i < lineInput.size(); ++i) {
            String row = lineInput.get(i);
            List<String> list = Arrays.asList(StringUtils.split(row, ' '));
            String nodePath = list.get(0);
            int x = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(nodePath, "node-x"), "-y"));
            int y = Integer.parseInt(ReaderUtil.stringAfter(nodePath, "-y"));
            int total = Integer.parseInt(ReaderUtil.stringBefore(list.get(1), "T"));
            int used = Integer.parseInt(ReaderUtil.stringBefore(list.get(2), "T"));
            nodes.put(new Point2D(x, y), Pair.of(used, total));
        }

//        System.out.println(nodes);

        Set<Pair<Point2D, Point2D>> used = new HashSet<>();

        int total = 0;
        for (Point2D pointA : nodes.keySet()) {
            for (Point2D pointB : nodes.keySet()) {
                if(pointB.equals(pointA)) {
                    continue;
                }
                Pair<Integer, Integer> nodeA = nodes.get(pointA);
                Pair<Integer, Integer> nodeB = nodes.get(pointB);
                if(nodeA.getLeft() > 0 && nodeA.getLeft() + nodeB.getLeft() <= nodeB.getRight()) {
                    if(!used.contains(Pair.of(pointA, pointB))) {
                        used.add(Pair.of(pointB, pointA));
                        total++;
                    }
                }
            }
        }
        System.out.println(total);

    }


    public static void second(List<String> lineInput) {
        Map<Point2D, Integer> totalMap = new HashMap<>();
        Map<Point2D, Integer> initialUsedMap = new HashMap<>();
        int topX = 0;
        int topY = 0;
        Point2D initialHole = null;
        for(int i = 2; i < lineInput.size(); ++i) {
            String row = lineInput.get(i);
            List<String> list = Arrays.asList(StringUtils.split(row, ' '));
            String nodePath = list.get(0);
            int x = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(nodePath, "node-x"), "-y"));
            if(x > topX) {
                topX = x;
            }
            int y = Integer.parseInt(ReaderUtil.stringAfter(nodePath, "-y"));
            if(y > topY) {
                topY = y;
            }
            int total = Integer.parseInt(ReaderUtil.stringBefore(list.get(1), "T"));
            int used = Integer.parseInt(ReaderUtil.stringBefore(list.get(2), "T"));
            Point2D point = new Point2D(x, y);
            if(used == 0) {
                initialHole = point;
            }
            totalMap.put(point, total);
            initialUsedMap.put(point, used);
        }
        Point2D target = new Point2D(0, 0);
        Point2D initialDataPoint = new Point2D(topX, 0);

        for (int j = 0; j <= topY; ++j) {
            for(int i = 0; i <= topX; ++i) {
                Point2D point = new Point2D(i, j);
                System.out.print("  " + StringUtils.leftPad(initialUsedMap.get(point) + "/" + totalMap.get(point), 7, ' ') + "  ");
//                if(point.equals(target)) {
//                    System.out.print("   X   ");
//                } else if(point.equals(initialDataPoint)) {
//                    System.out.print("   D   ");
//                } else if(initialUsedMap.get(point) == 0) {
//                    System.out.print('0');
//                } else {
//                    boolean canOffload = false;
//                    List<Point2D> neighbors = PointsUtil.newPoints(point, topX, topY);
//                    for (Point2D neighbor : neighbors) {
//                        if(totalMap.get(neighbor) >= initialUsedMap.get(point)) {
//                            canOffload = true;
//                            break;
//                        }
//                    }
//                    System.out.print(canOffload ? '.' : '#');
//                }
            }
            System.out.println();
        }


        State initialState = new State();
        initialState.dataPoint = initialDataPoint;
        initialState.usedMap = initialUsedMap;
        initialState.holePoint = initialHole;
        initialState.steps = 0;

//        int minDistance = Integer.MAX_VALUE;
//
//        Queue<State> queue = new PriorityQueue<>();
//        queue.add(initialState);
//
//        Set<String> processedStates = new HashSet<>();
//
//        outer: while(true) {
//            State state = queue.poll();
//            int distance = state.getDistance();
//            if(distance < minDistance) {
//                minDistance = distance;
//                System.out.println("Min distance: " + distance + ". Hole: " + state.holePoint + ". Data: " + state.dataPoint + ". Step " + state.steps);
//            }
//
//            String key = state.getKey(topX, topY);
//            if(processedStates.contains(key)) {
//                continue;
//            }
//            processedStates.add(key);
//
//            Point2D neighbor = state.holePoint;
//            List<Point2D> neighbors = PointsUtil.newPoints(neighbor, topX, topY);
//            for (Point2D pointToProcess : neighbors) {
//                int sumValue = state.usedMap.get(pointToProcess) + state.usedMap.get(neighbor);
//                if(sumValue <= totalMap.get(neighbor)) {
//                    if(state.usedMap.get(neighbor) > 0) {
//                        System.out.println("Merging");
//                    }
//                    State newState = new State();
//                    if(pointToProcess.equals(state.dataPoint)) {
//                        if(neighbor.equals(target)) {
//                            System.out.println(state.steps + 1);
//                            break outer;
//                        }
//                        newState.dataPoint = neighbor;
//                    } else {
//                        newState.dataPoint = state.dataPoint;
//                    }
//                    newState.usedMap = new HashMap<>(state.usedMap);
//                    newState.usedMap.put(pointToProcess, 0);
//                    newState.steps = state.steps + 1;
//                    newState.holePoint = pointToProcess;
//                    newState.usedMap.put(neighbor, sumValue);
//                    if(processedStates.contains(newState.getKey(topX, topY))) {
//                        continue;
//                    }
//                    queue.add(newState);
//                }
//            }
//        }

        System.out.println(34 + 33*5 + 1);

    }

    private static class State implements Comparable {

        Point2D dataPoint;
        Point2D holePoint;
        Map<Point2D, Integer> usedMap;
        int steps;

        String getKey(int topX, int topY) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j <= topY; ++j) {
                for(int i = 0; i <= topX; ++i) {
                    sb.append(usedMap.get(new Point2D(i, j)));
                    sb.append('|');
                }
            }
            sb.append(dataPoint.getX()).append('|').append(dataPoint.getY());
            return sb.toString();
        }

        int getValue() {
            return steps + getDistance();
        }

        int getDistance() {
            return holePoint.getX() + holePoint.getY() + dataPoint.getY() + dataPoint.getX() +
                    Math.abs(dataPoint.getX() - holePoint.getX()) + Math.abs(dataPoint.getY() - holePoint.getY());
        }
        @Override
        public int compareTo(Object o) {
            return Integer.compare(getValue(), ((State) o).getValue());
        }
    }


}
