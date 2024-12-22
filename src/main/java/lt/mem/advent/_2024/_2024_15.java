package lt.mem.advent._2024;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class _2024_15 {

    public static void main(String[] args) {
        List<String> input = ReaderUtil.readLineInput("_2024/15.txt");
        long timeMillis = System.currentTimeMillis();
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    public static void first(List<String> input) {
        Set<Point2D> walls = new HashSet<>();
        Set<Point2D> boxes = new HashSet<>();
        List<Direction> directions = new ArrayList<>();

        Point2D robot = null;
        boolean isDirection = false;
        int y = 0;
        for (String line : input) {
            if(!isDirection) {
                if(StringUtils.isBlank(line)) {
                    isDirection = true;
                } else {
                    for(int x = 0; x < line.length(); x++) {
                        char c = line.charAt(x);
                        Point2D point = new Point2D(x, y);
                        if(c == '@') {
                            robot = point;
                        } else if (c == '#') {
                            walls.add(point);
                        } else if (c == 'O') {
                            boxes.add(point);
                        }
                    }
                    ++y;
                }
            } else {
                for (char c : line.toCharArray()) {
                    Direction direction;
                    if(c == '<') {
                        direction = Direction.LEFT;
                    } else if (c == '^') {
                        direction = Direction.UP;
                    } else if (c == '>') {
                        direction = Direction.RIGHT;
                    } else {
                        direction = Direction.DOWN;
                    }
                    directions.add(direction);
                }
            }
        }

        for (Direction direction : directions) {
            Point2D nextPoint = PointsUtil.newPointNoGrid(robot, direction);
            if(walls.contains(nextPoint)) {
                continue;
            }

            if(boxes.contains(nextPoint)) {
                Point2D nextInLine = nextPoint;
                while(boxes.contains(nextInLine)) {
                    nextInLine = PointsUtil.newPointNoGrid(nextInLine, direction);
                }
                if(walls.contains(nextInLine)) {
                    continue;
                }

                boxes.add(nextInLine);
                boxes.remove(nextPoint);
            }

            robot = nextPoint;
        }

        long total = 0;
        for (Point2D box : boxes) {
            total += box.getX() + (box.getY() * 100);
        }

        System.out.println(total);
    }

    public static void second(List<String> input) {
        Set<Point2D> walls = new HashSet<>();
        Map<Point2D, Box> boxes = new HashMap<>();
        List<Direction> directions = new ArrayList<>();

        Point2D robot = null;
        boolean isDirection = false;
        int y = 0;
        for (String line : input) {
            if(!isDirection) {
                if(StringUtils.isBlank(line)) {
                    isDirection = true;
                } else {
                    for(int x = 0; x < line.length(); x++) {
                        char c = line.charAt(x);
                        Point2D leftPoint = new Point2D(x * 2, y);
                        Point2D rightPoint = new Point2D((x * 2) + 1, y);
                        if(c == '@') {
                            robot = leftPoint;
                        } else if (c == '#') {
                            walls.add(leftPoint);
                            walls.add(rightPoint);
                        } else if (c == 'O') {
                            Box box = new Box(leftPoint, rightPoint);
                            boxes.put(leftPoint, box);
                            boxes.put(rightPoint, box);
                        }
                    }
                    ++y;
                }
            } else {
                for (char c : line.toCharArray()) {
                    Direction direction;
                    if(c == '<') {
                        direction = Direction.LEFT;
                    } else if (c == '^') {
                        direction = Direction.UP;
                    } else if (c == '>') {
                        direction = Direction.RIGHT;
                    } else {
                        direction = Direction.DOWN;
                    }
                    directions.add(direction);
                }
            }
        }

        for (Direction direction : directions) {
            Point2D nextPoint = PointsUtil.newPointNoGrid(robot, direction);
            if(walls.contains(nextPoint)) {
                continue;
            }

            if(boxes.containsKey(nextPoint)) {
                Set<Box> allBoxes = new HashSet<>();
                Set<Box> nextInLine = new HashSet<>();
                nextInLine.add(boxes.get(nextPoint));
                allBoxes.add(boxes.get(nextPoint));
                boolean walled = false;
                while(CollectionUtils.isNotEmpty(nextInLine)) {
                    Set<Box> newBoxes = new HashSet<>();
                    Set<Point2D> newPoints = new HashSet<>();
                    for (Box box : nextInLine) {
                        newPoints.addAll(box.newPoints(direction));
                    }
                    for (Point2D newPoint : newPoints) {
                        if(walls.contains(newPoint)) {
                            walled = true;
                            break;
                        }
                        if(boxes.containsKey(newPoint)) {
                            newBoxes.add(boxes.get(newPoint));
                        }
                    }
                    if(walled) {
                        break;
                    }
                    allBoxes.addAll(newBoxes);
                    nextInLine = newBoxes;
                }
                if(walled) {
                    continue;
                }

                for (Box box : allBoxes) {
                    boxes.remove(box.left);
                    boxes.remove(box.right);
                }
                for (Box box : allBoxes) {
                    box.right = PointsUtil.newPointNoGrid(box.right, direction);
                    box.left = PointsUtil.newPointNoGrid(box.left, direction);
                    boxes.put(box.right, box);
                    boxes.put(box.left, box);
                }
            }

            robot = nextPoint;
        }

        long total = 0;
        Set<Box> boxesToCalculate = new HashSet<>(boxes.values());
        for (Box box : boxesToCalculate) {
            total += box.left.getX() + (box.left.getY() * 100);
        }

        System.out.println(total);

    }

    private static class Box {
        Point2D left;
        Point2D right;

        public Box(Point2D left, Point2D right) {
            this.left = left;
            this.right = right;
        }

        Set<Point2D> newPoints(Direction direction) {
            if(direction.equals(Direction.RIGHT)) {
                return Collections.singleton(PointsUtil.newPointNoGrid(right, direction));
            } else if(direction.equals(Direction.LEFT)) {
                return Collections.singleton(PointsUtil.newPointNoGrid(left, direction));
            } else {
                Set<Point2D> points = new HashSet<>();
                points.add(PointsUtil.newPointNoGrid(left, direction));
                points.add(PointsUtil.newPointNoGrid(right, direction));
                return points;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Box)) return false;
            Box box = (Box) o;
            return Objects.equals(left, box.left) && Objects.equals(right, box.right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }


}
