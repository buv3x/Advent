package lt.mem.advent._2024;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class _2024_21 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/21.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Set<Point2D> numberPoints = Arrays.stream(NumberPad.values()).map(NumberPad::getPoint).collect(Collectors.toSet());
    private static final Set<Point2D> arrowPoints = Arrays.stream(ArrowPad.values()).map(ArrowPad::getPoint).collect(Collectors.toSet());

    private static final Map<Pair<Point2D, Point2D>, Integer> levelTwoCache = new HashMap<>();


    private static List<List<NumberPad>> fillNumbers(List<String> input) {
        List<List<NumberPad>> allNumbers = new ArrayList<>();
        for (String row : input) {
            List<NumberPad> numbers = new ArrayList<>();
            for (char c : row.toCharArray()) {
                numbers.add(NumberPad.getByChar(c));
            }
            allNumbers.add(numbers);
        }
        return allNumbers;
    }

    private static List<List<Direction>> getPath(Point2D currentPoint, Point2D target,
                                                 Set<Point2D> usedPoints, List<Direction> directions, Set<Point2D> grid) {
        List<List<Direction>> result = new ArrayList<>();
        if(currentPoint.equals(target)) {
            result.add(directions);
            return result;
        }

        Set<Point2D> newUsedPoints = new HashSet<>(usedPoints);
        newUsedPoints.add(currentPoint);
        for (Direction direction : Direction.values()) {
            Point2D newPoint = PointsUtil.newPointNoGrid(currentPoint, direction);
            if(grid.contains(newPoint) && !usedPoints.contains(newPoint)) {
                List<Direction> newDirections = new ArrayList<>(directions);
                newDirections.add(direction);
                result.addAll(getPath(newPoint, target, newUsedPoints, newDirections, grid));
            }
        }

        return result;
    }

    public static void second(List<String> input) {
        List<List<NumberPad>> allNumbers = fillNumbers(input);

        long globalTotal = 0;
        for (List<NumberPad> number : allNumbers) {
            StringBuilder value = new StringBuilder();
            for(int i = 0; i < number.size() - 1; ++i) {
                value.append(number.get(i).c);
            }
            long total = 0;
            for(int i = 0; i < number.size(); ++i) {
                Point2D start = i == 0 ? NumberPad._A.point : number.get(i - 1).point;
                Point2D end = number.get(i).point;
                List<List<Direction>> path = getPath(start, end, Collections.emptySet(), Collections.emptyList(), numberPoints);
                path.sort(Comparator.comparing(List::size));

                long minTotal2 = calculate(1, path);

                total += minTotal2;
            }

            globalTotal += total * Integer.parseInt(value.toString());
        }

        System.out.println(globalTotal);
    }

    private static final int TOTAL_LEVELS = 25;

    private static Map<Integer, Map<Pair<Point2D, Point2D>, Long>> cache = new HashMap<>();

    private static long calculate(int levelIndex, List<List<Direction>> path) {
        if(!cache.containsKey(levelIndex)) {
            cache.put(levelIndex, new HashMap<>());
        }

        Map<Pair<Point2D, Point2D>, Long> levelCache = cache.get(levelIndex);

        long minTotal = Long.MAX_VALUE;
        for (List<Direction> directions : path) {
            List<ArrowPad> level = new ArrayList<>();
            for (Direction direction : directions) {
                level.add(ArrowPad.getByDirection(direction));
            }
            level.add(ArrowPad._A);
            long total2 = 0;
            for(int j = 0; j < level.size(); ++j) {
                Point2D start2 = j == 0 ? ArrowPad._A.point : level.get(j - 1).point;
                Point2D end2 = level.get(j).point;
                Pair<Point2D, Point2D> key = Pair.of(start2, end2);
                if(levelCache.containsKey(key)) {
                    total2 += levelCache.get(key);
                } else {
                    List<List<Direction>> path2 = getPath(start2, end2, Collections.emptySet(), Collections.emptyList(), arrowPoints);
                    path2.sort(Comparator.comparing(List::size));

                    long minTotal3 = (levelIndex == TOTAL_LEVELS - 1) ? calculateLastLevel(path2) : calculate(levelIndex + 1, path2);

                    total2 += minTotal3;
                    levelCache.put(key, minTotal3);
                }
                if(total2 > minTotal) {
                    break;
                }
            }
            if(total2 < minTotal) {
                minTotal = total2;
            }
        }

//        System.out.println(levelIndex + ": " + minTotal);

        return minTotal;
    }

    private static int calculateLastLevel(List<List<Direction>> path2) {
        int minTotal3 = Integer.MAX_VALUE;
        for (List<Direction> directions3 : path2) {
            List<ArrowPad> level3 = new ArrayList<>();
            for (Direction direction : directions3) {
                level3.add(ArrowPad.getByDirection(direction));
            }
            level3.add(ArrowPad._A);
            int total3 = getTotalLastLevel(minTotal3, level3);
            if(total3 < minTotal3) {
                minTotal3 = total3;
            }
        }
        return minTotal3;
    }

    private static int getTotalLastLevel(int minTotal3, List<ArrowPad> level) {
        int total3 = 0;
        for(int k = 0; k < level.size(); ++k) {
            Point2D start3 = k == 0 ? ArrowPad._A.point : level.get(k - 1).point;
            Point2D end3 = level.get(k).point;
            total3 += PointsUtil.squareDistance(start3, end3) + 1;
            if(total3 > minTotal3) {
                break;
            }
        }
        return total3;
    }

    private enum ArrowPad{
        UP(new Point2D(1,0), Direction.UP),
        LEFT(new Point2D(0,1), Direction.LEFT),
        RIGHT(new Point2D(2,1), Direction.RIGHT),
        DOWN(new Point2D(1,1), Direction.DOWN),
        _A(new Point2D(2,0), null);

        private final Point2D point;
        private final Direction direction;

        ArrowPad(Point2D point, Direction direction) {
            this.point = point;
            this.direction = direction;
        }

        public Point2D getPoint() {
            return point;
        }

        public static ArrowPad getByDirection(Direction direction) {
            for (ArrowPad value : ArrowPad.values()) {
                if(direction.equals(value.direction)) {
                    return value;
                }
            }
            return null;
        }
    }

    private enum NumberPad{
        _0(new Point2D(1,3), '0'),
        _1(new Point2D(0,2), '1'),
        _2(new Point2D(1,2), '2'),
        _3(new Point2D(2,2), '3'),
        _4(new Point2D(0,1), '4'),
        _5(new Point2D(1,1), '5'),
        _6(new Point2D(2,1), '6'),
        _7(new Point2D(0,0), '7'),
        _8(new Point2D(1,0), '8'),
        _9(new Point2D(2,0), '9'),
        _A(new Point2D(2,3), 'A');

        private final Point2D point;
        private final char c;

        NumberPad(Point2D point, char c) {
            this.point = point;
            this.c = c;
        }

        public Point2D getPoint() {
            return point;
        }

        public static NumberPad getByChar(char c) {
            for (NumberPad value : NumberPad.values()) {
                if(c == value.c) {
                    return value;
                }
            }
            return null;
        }
    }





    public static void first(List<String> input) {
        List<List<NumberPad>> allNumbers = fillNumbers(input);

        long globalTotal = 0;
        for (List<NumberPad> number : allNumbers) {
            StringBuilder value = new StringBuilder();
            for(int i = 0; i < number.size() - 1; ++i) {
                value.append(number.get(i).c);
            }
            int total = 0;
            for(int i = 0; i < number.size(); ++i) {
                Point2D start = i == 0 ? NumberPad._A.point : number.get(i - 1).point;
                Point2D end = number.get(i).point;
                List<List<Direction>> path = getPath(start, end, Collections.emptySet(), Collections.emptyList(), numberPoints);
                path.sort(Comparator.comparing(List::size));
                int minTotal2 = Integer.MAX_VALUE;
                for (List<Direction> directions2 : path) {
                    List<ArrowPad> level2 = new ArrayList<>();
                    for (Direction direction : directions2) {
                        level2.add(ArrowPad.getByDirection(direction));
                    }
                    level2.add(ArrowPad._A);
                    int total2 = 0;
                    for(int j = 0; j < level2.size(); ++j) {
                        Point2D start2 = j == 0 ? ArrowPad._A.point : level2.get(j - 1).point;
                        Point2D end2 = level2.get(j).point;
                        Pair<Point2D, Point2D> key = Pair.of(start2, end2);
                        if(levelTwoCache.containsKey(key)) {
                            total2 += levelTwoCache.get(key);
                        } else {
                            List<List<Direction>> path2 = getPath(start2, end2, Collections.emptySet(), Collections.emptyList(), arrowPoints);
                            int minTotal3 = calculateLastLevel(path2);
                            total2 += minTotal3;
                            levelTwoCache.put(key, minTotal3);
                        }
                        if(total2 > minTotal2) {
                            break;
                        }
                    }
                    if(total2 < minTotal2) {
                        minTotal2 = total2;
                    }
                }
                total += minTotal2;
            }

            globalTotal += (long) total * Integer.parseInt(value.toString());
        }

        System.out.println(globalTotal);
    }


}
