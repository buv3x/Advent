package lt.mem.advent._2023;

import lt.mem.advent.structure.DirectedPoint2D;
import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class _2023_16 {

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        List<String> input = ReaderUtil.readLineInput("_2023/16.txt");
//        first(input);
        second(input);
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public static void first(List<String> input) {
        List<List<Cell>> cells = new ArrayList<>();
        for (String line : input) {
            List<Cell> cellRow = new ArrayList<>();
            cells.add(cellRow);
            for (char character : line.toCharArray()) {
                cellRow.add(new Cell(CellType.parseCharacter(character)));
            }
        }

        DirectedPoint2D initialPoints = new DirectedPoint2D(new Point2D(0, 0), Direction.RIGHT);
        List<List<Cell>> cellsToProcess = new ArrayList<>(cells);
        int total = calculateFromInitial(initialPoints, cellsToProcess);
        System.out.println(total);
    }

    public static void second(List<String> input) {
        List<List<Cell>> cells = new ArrayList<>();
        for (String line : input) {
            List<Cell> cellRow = new ArrayList<>();
            cells.add(cellRow);
            for (char character : line.toCharArray()) {
                cellRow.add(new Cell(CellType.parseCharacter(character)));
            }
        }

        int max = 0;
        for (Direction direction : Direction.values()) {
            List<Point2D> edgePoints = new ArrayList<>();
            switch (direction) {
                case DOWN: {
                    for(int i = 0; i < cells.get(0).size(); ++i) {
                        edgePoints.add(new Point2D(i, 0));
                    }
                    break;
                }
                case UP: {
                    for(int i = 0; i < cells.get(0).size(); ++i) {
                        edgePoints.add(new Point2D(i, cells.size() - 1));
                    }
                    break;
                }
                case RIGHT: {
                    for(int i = 0; i < cells.size(); ++i) {
                        edgePoints.add(new Point2D(0, i));
                    }
                    break;
                }
                case LEFT: {
                    for(int i = 0; i < cells.size(); ++i) {
                        edgePoints.add(new Point2D(cells.get(0).size() - 1, i));
                    }
                    break;
                }
            }

            for (Point2D edgePoint : edgePoints) {
                DirectedPoint2D initialPoint = new DirectedPoint2D(edgePoint, direction);
                for (List<Cell> cellRow : cells) {
                    for (Cell cell : cellRow) {
                        cell.visitedWithDirections.clear();
                    }
                }
                max = Math.max(max, calculateFromInitial(initialPoint, cells));
            }
        }

        System.out.println(max);
    }

    private static int calculateFromInitial(DirectedPoint2D initialPoints, List<List<Cell>> cellsToProcess) {
        List<DirectedPoint2D> pointsToProcess = new ArrayList<>(List.of(initialPoints));
        while(CollectionUtils.isNotEmpty(pointsToProcess)) {
            List<DirectedPoint2D> newPointsToProcess = new ArrayList<>();
            for (DirectedPoint2D point : pointsToProcess) {
                List<DirectedPoint2D> newPoints = processPoint(point, cellsToProcess);
                if(CollectionUtils.isNotEmpty(newPoints)) {
                    newPointsToProcess.addAll(newPoints);
                }
            }
            pointsToProcess = newPointsToProcess;
        }

        int total = 0;
        for (List<Cell> cellRow : cellsToProcess) {
            for (Cell cell : cellRow) {
                if(CollectionUtils.isNotEmpty(cell.visitedWithDirections)) {
                    total++;
                }
            }
        }
        return total;
    }

    private static List<DirectedPoint2D> processPoint(DirectedPoint2D point, List<List<Cell>> cells) {
        Cell cell = cells.get(point.getPoint().getY()).get(point.getPoint().getX());
        if(cell.visitedWithDirections.contains(point.getDirection())) {
            return Collections.emptyList();
        }
        cell.visitedWithDirections.add(point.getDirection());

        List<Direction> newDirections = new ArrayList<>();
        switch (cell.cellType) {
            case BLANK: {
                newDirections.add(point.getDirection());
                break;
            }
            case BACKWARD_MIRROR: {
                switch (point.getDirection()) {
                    case UP: {
                        newDirections.add(Direction.LEFT);
                        break;
                    }
                    case LEFT: {
                        newDirections.add(Direction.UP);
                        break;
                    }
                    case RIGHT: {
                        newDirections.add(Direction.DOWN);
                        break;
                    }
                    case DOWN: {
                        newDirections.add(Direction.RIGHT);
                        break;
                    }
                }
                break;
            }
            case FORWARD_MIRROR: {
                switch (point.getDirection()) {
                    case UP: {
                        newDirections.add(Direction.RIGHT);
                        break;
                    }
                    case RIGHT: {
                        newDirections.add(Direction.UP);
                        break;
                    }
                    case LEFT: {
                        newDirections.add(Direction.DOWN);
                        break;
                    }
                    case DOWN: {
                        newDirections.add(Direction.LEFT);
                        break;
                    }
                }
                break;
            }
            case HORIZONTAL_SPLITTER: {
                switch (point.getDirection()) {
                    case RIGHT: {
                        newDirections.add(Direction.RIGHT);
                        break;
                    }
                    case LEFT: {
                        newDirections.add(Direction.LEFT);
                        break;
                    }
                    case UP:
                    case DOWN: {
                        newDirections.add(Direction.LEFT);
                        newDirections.add(Direction.RIGHT);
                        break;
                    }
                }
                break;
            }
            case VERTICAL_SPLITTER: {
                switch (point.getDirection()) {
                    case UP: {
                        newDirections.add(Direction.UP);
                        break;
                    }
                    case DOWN: {
                        newDirections.add(Direction.DOWN);
                        break;
                    }
                    case RIGHT:
                    case LEFT: {
                        newDirections.add(Direction.UP);
                        newDirections.add(Direction.DOWN);
                        break;
                    }
                }
                break;
            }
        }

        List<DirectedPoint2D> newPoints = new ArrayList<>();
        for (Direction newDirection : newDirections) {
            Point2D newPoint = PointsUtil.newPoint(point.getPoint(), newDirection, cells.get(0).size() - 1, cells.size() - 1);
            if(newPoint != null) {
                newPoints.add(new DirectedPoint2D(newPoint, newDirection));
            }
        }

        return newPoints;
    }

    private static class Cell {
        CellType cellType;
        Set<Direction> visitedWithDirections = new HashSet<>();

        public Cell(CellType cellType) {
            this.cellType = cellType;
        }
    }

    private enum CellType {
        BLANK('.'),
        FORWARD_MIRROR('/'),
        BACKWARD_MIRROR('\\'),
        VERTICAL_SPLITTER('|'),
        HORIZONTAL_SPLITTER('-');

        private final Character character;

        CellType(Character character) {
            this.character = character;
        }

        static CellType parseCharacter(Character character) {
            for (CellType value : values()) {
                if(value.character.equals(character)) {
                    return value;
                }
            }
            throw new IllegalArgumentException();
        }

    }

}
