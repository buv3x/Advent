package lt.mem.advent._2017;

import lt.mem.advent.structure.Direction;
import lt.mem.advent.structure.Point2D;
import lt.mem.advent.structure.Turn;
import lt.mem.advent.util.PointsUtil;
import lt.mem.advent.util.ReaderUtil;

import java.util.*;

public class _2017_19 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2017/19.txt");
        long timeMillis = System.currentTimeMillis();
        first(lineInput);
//        second(lineInput);
        System.out.println("Time: " + (System.currentTimeMillis() - timeMillis));
    }

    private static final Map<Point2D, Character> grid = new HashMap<>();

    public static void first(List<String> lineInput) {
        process(lineInput);
    }

    private static void process(List<String> lineInput) {
        Direction direction = Direction.DOWN;
        Point2D current = null;
        for(int i = 0; i < lineInput.size(); ++i) {
            String row = lineInput.get(i);
            for(int j = 0; j < row.length(); ++j) {
                if(row.charAt(j) != ' ') {
                    Point2D point = new Point2D(j, i);
                    if(i == 0) {
                        current = point;
                    }
                    grid.put(point, row.charAt(j));
                }
            }
        }


        StringBuilder sb = new StringBuilder();
        int counter = 0;
        while(grid.containsKey(current)) {
            Character character = grid.get(current);
            if(character == '-' || character == '|') {
                // Do nothing
            } else if (character == '+') {
                Point2D leftPoint = PointsUtil.newPointNoGrid(current, PointsUtil.applyTurn(direction, Turn.LEFT));
                if(grid.containsKey(leftPoint)) {
                    direction = PointsUtil.applyTurn(direction, Turn.LEFT);
                } else {
                    direction = PointsUtil.applyTurn(direction, Turn.RIGHT);
                }
            } else {
                sb.append(character);
            }
            current = PointsUtil.newPointNoGrid(current, direction);
            counter++;
        }

        System.out.println("Code: " + sb);
        System.out.println("Steps: " + counter);
    }

    public static void second(List<String> lineInput) {

    }

}
