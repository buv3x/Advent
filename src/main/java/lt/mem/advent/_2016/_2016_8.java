package lt.mem.advent._2016;

import lt.mem.advent.structure.Point2D;
import lt.mem.advent.util.ReaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class _2016_8 {

    public static void main(String[] args) {
        List<String> lineInput = ReaderUtil.readLineInput("_2016/8.txt");
//        first(lineInput);
        second(lineInput);
    }

    private static int WIDTH = 50;
    private static int HEIGHT = 6;

    private static final Map<Point2D, Boolean> grid = new HashMap<>();

    public static void first(List<String> lineInput) {
        process(lineInput);

        int count = 0;
        for (Boolean value : grid.values()) {
            if(value) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void process(List<String> lineInput) {
        for(int i = 0; i < WIDTH; ++i) {
            for(int j = 0; j < HEIGHT; ++j) {
                grid.put(new Point2D(i, j), false);
            }
        }
        for (String row : lineInput) {
            if(row.startsWith("rect ")) {
                int x = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, "rect "), "x"));
                int y = Integer.parseInt(ReaderUtil.stringAfter(ReaderUtil.stringAfter(row, "rect "), "x"));
                for(int i = 0; i < x; ++i) {
                    for(int j = 0; j < y; ++j) {
                        grid.put(new Point2D(i, j), true);
                    }
                }
            } else if(row.startsWith("rotate column ")) {
                int index = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, " x="), " by "));
                int by = Integer.parseInt(ReaderUtil.stringAfter(row, " by "));
                Map<Point2D, Boolean> newMap = new HashMap<>();
                for (int j = 0; j < HEIGHT; ++j) {
                    Point2D newPoint = new Point2D(index, j);
                    newMap.put(newPoint, grid.get(new Point2D(index, (j + HEIGHT - by) % HEIGHT)));
                }
                grid.putAll(newMap);
            } else if(row.startsWith("rotate row ")) {
                int index = Integer.parseInt(ReaderUtil.stringBefore(ReaderUtil.stringAfter(row, " y="), " by "));
                int by = Integer.parseInt(ReaderUtil.stringAfter(row, " by "));
                Map<Point2D, Boolean> newMap = new HashMap<>();
                for (int i = 0; i < WIDTH; ++i) {
                    Point2D newPoint = new Point2D(i, index);
                    newMap.put(newPoint, grid.get(new Point2D((i + WIDTH - by) % WIDTH, index)));
                }
                grid.putAll(newMap);
            }
        }
    }

    public static void second(List<String> lineInput) {
        process(lineInput);

        for(int j = 0; j < HEIGHT; ++j) {
            for(int i = 0; i < WIDTH; ++i) {
                System.out.print(grid.get(new Point2D(i, j)) ? "#" : ".");
            }
            System.out.println();
        }
    }

}
