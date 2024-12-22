package lt.mem.advent._2024;

import lt.mem.advent.structure.DirectedPoint2D;
import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2024_16 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/16_ex.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Set<Point2D> walls = new HashSet<>();
    private static Point2D start;
    private static Point2D end;
    private static long minScore = Long.MAX_VALUE;

    private static final Map<DirectedPoint2D, Long> scores = new HashMap<>();
    public static void first(List<String> input) {
        fillGrid(input);

        process(start, Direction.RIGHT, 0);

        System.out.println(minScore);
    }

    private static void fillGrid(List<String> input) {
        for(int i = 0; i < input.size(); ++i) {
            for(int j = 0; j < input.get(i).length(); ++j) {
                char c = input.get(i).charAt(j);
                Point2D point = new Point2D(j, i);
                if(c == '#') {
                    walls.add(point);
                } else if (c == 'E') {
                    end = point;
                } else if (c == 'S') {
                    start = point;
                }
            }
        }
    }


    private static final Map<DirectedPoint2D, Tile> tiles = new HashMap<>();

    public static void second(List<String> input) {
        fillGrid(input);

        DirectedPoint2D startDirected = new DirectedPoint2D(start, Direction.RIGHT);
        Tile startTile = new Tile(0, startDirected);
        startTile.optimalPath.add(start);
        tiles.put(startDirected, startTile);

        Queue<Tile> queue = new PriorityQueue<>();
        queue.add(startTile);

        while(true) {
            Tile polled = queue.poll();
            DirectedPoint2D toProcess = polled.directedPoint;

            if(polled.optimalScore > minScore) {
                break;
            }

            if(end.equals(toProcess.getPoint())) {
                if(polled.optimalScore < minScore) {
                    minScore = polled.optimalScore;
                }
            }

            Point2D straight = PointsUtil.newPointNoGrid(toProcess.getPoint(), toProcess.getDirection());
            if(!walls.contains(straight)) {
                DirectedPoint2D straightDirected = new DirectedPoint2D(straight, toProcess.getDirection());
                long optimalScore = polled.optimalScore + 1;
                extracted(queue, polled, straight, straightDirected, optimalScore);
            }

            Direction rightDirection = PointsUtil.applyTurn(toProcess.getDirection(), Turn.RIGHT);
            Point2D right = PointsUtil.newPointNoGrid(toProcess.getPoint(), rightDirection);
            if(!walls.contains(right)) {
                DirectedPoint2D rightDirected = new DirectedPoint2D(toProcess.getPoint(), rightDirection);
                long optimalScore = polled.optimalScore + 1000;
                extracted(queue, polled, toProcess.getPoint(), rightDirected, optimalScore);
            }

            Direction leftDirection = PointsUtil.applyTurn(toProcess.getDirection(), Turn.LEFT);
            Point2D left = PointsUtil.newPointNoGrid(toProcess.getPoint(), leftDirection);
            if(!walls.contains(left)) {
                DirectedPoint2D leftDirected = new DirectedPoint2D(toProcess.getPoint(), leftDirection);
                long optimalScore = polled.optimalScore + 1000;
                extracted(queue, polled, toProcess.getPoint(), leftDirected, optimalScore);
            }

        }

        Tile tileUp = tiles.get(new DirectedPoint2D(end, Direction.UP));
        Tile tileLeft = tiles.get(new DirectedPoint2D(end, Direction.LEFT));
        Tile tileRight = tiles.get(new DirectedPoint2D(end, Direction.RIGHT));
        Tile tileDown = tiles.get(new DirectedPoint2D(end, Direction.DOWN));

        Set<Point2D> path = new HashSet<>();
        if(tileUp != null && tileUp.optimalScore == minScore) {
            path.addAll(tileUp.optimalPath);
        }
        if(tileLeft != null && tileLeft.optimalScore == minScore) {
            path.addAll(tileLeft.optimalPath);
        }
        if(tileRight != null && tileRight.optimalScore == minScore) {
            path.addAll(tileRight.optimalPath);
        }
        if(tileDown != null && tileDown.optimalScore == minScore) {
            path.addAll(tileDown.optimalPath);
        }

        System.out.println(path.size());
    }

    private static void extracted(Queue<Tile> queue, Tile polled, Point2D straight, DirectedPoint2D rightDirected, long optimalScore) {
        if(tiles.containsKey(rightDirected)) {
            Tile straightTile = tiles.get(rightDirected);
            if(straightTile.optimalScore > optimalScore) {
                straightTile = new Tile(optimalScore, rightDirected);
                tiles.put(rightDirected, straightTile);
                straightTile.optimalPath = new HashSet<>(polled.optimalPath);
                straightTile.optimalPath.add(straight);
                queue.add(straightTile);
            } else if(straightTile.optimalScore == optimalScore) {
                straightTile.optimalPath.addAll(polled.optimalPath);
            }
        } else {
            Tile straightTile = new Tile(optimalScore, rightDirected);
            straightTile.optimalPath = new HashSet<>(polled.optimalPath);
            straightTile.optimalPath.add(straight);
            tiles.put(rightDirected, straightTile);
            queue.add(straightTile);
        }
    }

    private static class Tile implements Comparable {
        long optimalScore = 0;
        DirectedPoint2D directedPoint;
        Set<Point2D> optimalPath = new HashSet<>();

        public Tile(long optimalScore, DirectedPoint2D directedPoint) {
            this.optimalScore = optimalScore;
            this.directedPoint = directedPoint;
        }

        @Override
        public int compareTo(Object o) {
            return Long.compare(optimalScore, ((Tile) o).optimalScore);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Tile)) return false;
            Tile tile = (Tile) o;
            return directedPoint.equals(tile.directedPoint);
        }

        @Override
        public int hashCode() {
            return Objects.hash(directedPoint);
        }
    }

    public static void process(Point2D point, Direction direction, long score) {
        DirectedPoint2D directedPoint = new DirectedPoint2D(point, direction);

        if(score >= minScore) {
            return;
        }
        if(scores.containsKey(directedPoint) && scores.get(directedPoint) <= score) {
            return;
        }

        scores.put(directedPoint, score);

        if(point.equals(end)) {
//            System.out.println(minScore);
            minScore = score;
            return;
        }

        Point2D straight = PointsUtil.newPointNoGrid(point, direction);
        if(!walls.contains(straight)) {
            process(straight, direction, score + 1);
        }

        Direction rightDirection = PointsUtil.applyTurn(direction, Turn.RIGHT);
        Point2D right = PointsUtil.newPointNoGrid(point, rightDirection);
        if(!walls.contains(right)) {
            process(point, rightDirection, score + 1000);
        }

        Direction leftDirection = PointsUtil.applyTurn(direction, Turn.LEFT);
        Point2D left = PointsUtil.newPointNoGrid(point, leftDirection);
        if(!walls.contains(left)) {
            process(point, leftDirection, score + 1000);
        }

    }



}
